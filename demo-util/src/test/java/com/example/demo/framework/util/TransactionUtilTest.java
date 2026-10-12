package com.example.demo.framework.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

class TransactionUtilTest {
    private final LocalTransactionManager manager = new LocalTransactionManager();
    private final TransactionTemplate template = new TransactionTemplate(manager);

    @AfterEach
    void clearsThreadState() {
        TransactionSynchronizationManager.clear();
    }

    @Test
    void callbacksRunInRegistrationOrderOnlyAfterCommit() {
        List<String> events = new ArrayList<>();
        template.executeWithoutResult(status -> {
            TransactionUtil.afterCommit(() -> events.add("first"));
            TransactionUtil.afterCommit(() -> events.add("second"));
            assertThat(events).isEmpty();
        });
        assertThat(events).containsExactly("first", "second");
        assertThat(manager.commits).isEqualTo(1);
    }

    @Test
    void rollbackOnlySuppressesCallbacks() {
        AtomicInteger calls = new AtomicInteger();
        template.executeWithoutResult(status -> {
            TransactionUtil.afterCommit(calls::incrementAndGet);
            status.setRollbackOnly();
        });
        assertThat(calls).hasValue(0);
        assertThat(manager.rollbacks).isEqualTo(1);
    }

    @Test
    void businessExceptionRollsBackWithoutExecutingCallback() {
        AtomicInteger calls = new AtomicInteger();
        RuntimeException failure = new RuntimeException("business failure");
        assertThatThrownBy(() -> template.executeWithoutResult(status -> {
            TransactionUtil.afterCommit(calls::incrementAndGet);
            throw failure;
        })).isSameAs(failure);
        assertThat(calls).hasValue(0);
        assertThat(manager.rollbacks).isEqualTo(1);
    }

    @Test
    void participatingTransactionWaitsForOuterCommit() {
        AtomicInteger calls = new AtomicInteger();
        template.executeWithoutResult(outer -> {
            template.executeWithoutResult(inner -> TransactionUtil.afterCommit(calls::incrementAndGet));
            assertThat(calls).hasValue(0);
        });
        assertThat(calls).hasValue(1);
        assertThat(manager.commits).isEqualTo(1);
    }

    @Test
    void callbackFailurePropagatesAfterDatabaseCommit() {
        RuntimeException failure = new RuntimeException("notification failure");
        assertThatThrownBy(() -> template.executeWithoutResult(status ->
            TransactionUtil.afterCommit(() -> { throw failure; }))).isSameAs(failure);
        assertThat(manager.commits).isEqualTo(1);
        assertThat(manager.rollbacks).isZero();
    }

    @Test
    void failureHandlerReceivesOriginalExceptionAndAllowsNextCallback() {
        RuntimeException failure = new RuntimeException("notification failure");
        List<RuntimeException> errors = new ArrayList<>();
        AtomicInteger calls = new AtomicInteger();
        template.executeWithoutResult(status -> {
            TransactionUtil.afterCommit(() -> { throw failure; }, errors::add);
            TransactionUtil.afterCommit(calls::incrementAndGet);
        });
        assertThat(errors).containsExactly(failure);
        assertThat(calls).hasValue(1);
    }

    @Test
    void handlerFailurePropagatesAndErrorIsNotIntercepted() {
        RuntimeException handled = new RuntimeException("handled");
        RuntimeException handlerFailure = new RuntimeException("handler failure");
        assertThatThrownBy(() -> template.executeWithoutResult(status ->
            TransactionUtil.afterCommit(() -> { throw handled; }, ignored -> { throw handlerFailure; })))
            .isSameAs(handlerFailure);
        AssertionError fatal = new AssertionError("fatal");
        assertThatThrownBy(() -> template.executeWithoutResult(status ->
            TransactionUtil.afterCommit(() -> { throw fatal; }, ignored -> fail("must not handle Error"))))
            .isSameAs(fatal);
    }

    @Test
    void noActiveTransactionOrNoSynchronizationIsRejected() {
        assertThatThrownBy(() -> TransactionUtil.afterCommit(() -> {})).isInstanceOf(IllegalStateException.class);
        TransactionSynchronizationManager.setActualTransactionActive(true);
        assertThatThrownBy(() -> TransactionUtil.afterCommit(() -> {})).isInstanceOf(IllegalStateException.class);
        TransactionSynchronizationManager.setActualTransactionActive(false);
        TransactionSynchronizationManager.initSynchronization();
        assertThatThrownBy(() -> TransactionUtil.afterCommit(() -> {})).isInstanceOf(IllegalStateException.class);
        assertThat(TransactionSynchronizationManager.getSynchronizations()).isEmpty();
    }

    @Test
    void nullCallbacksAreRejectedBeforeRegistration() {
        assertThatThrownBy(() -> TransactionUtil.afterCommit(null)).isInstanceOf(NullPointerException.class).hasMessage("action 不能为空");
        assertThatThrownBy(() -> TransactionUtil.afterCommit(null, ignored -> {})).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> TransactionUtil.afterCommit(() -> {}, null)).isInstanceOf(NullPointerException.class).hasMessage("onFailure 不能为空");
    }

    /** Exercises Spring's real commit/rollback callback lifecycle without a database. */
    private static class LocalTransactionManager extends AbstractPlatformTransactionManager {
        private final LocalTransaction transaction = new LocalTransaction();
        private int commits;
        private int rollbacks;
        protected Object doGetTransaction() { return transaction; }
        protected boolean isExistingTransaction(Object value) { return transaction.active; }
        protected void doBegin(Object value, TransactionDefinition definition) { transaction.active = true; }
        protected void doCommit(DefaultTransactionStatus status) { commits++; }
        protected void doRollback(DefaultTransactionStatus status) { rollbacks++; }
        protected void doSetRollbackOnly(DefaultTransactionStatus status) { transaction.rollbackOnly = true; }
        protected void doCleanupAfterCompletion(Object value) { transaction.active = false; transaction.rollbackOnly = false; }
    }

    private static class LocalTransaction implements SmartTransactionObject {
        private boolean active;
        private boolean rollbackOnly;
        public boolean isRollbackOnly() { return rollbackOnly; }
        public void flush() {}
    }
}
