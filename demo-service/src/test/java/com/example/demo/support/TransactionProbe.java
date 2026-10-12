package com.example.demo.support;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/** Verifies real Spring transaction advice; it does not simulate database persistence. */
public class TransactionProbe extends AbstractPlatformTransactionManager {
    public int commits;
    public int rollbacks;

    @SuppressWarnings("unchecked")
    public <T> T wrap(T target) {
        TransactionInterceptor advice = new TransactionInterceptor();
        advice.setTransactionManager(this);
        advice.setTransactionAttributeSource(new AnnotationTransactionAttributeSource());
        ProxyFactory factory = new ProxyFactory(target);
        factory.setProxyTargetClass(true);
        factory.addAdvice(advice);
        return (T) factory.getProxy();
    }

    protected Object doGetTransaction() { return new Object(); }
    protected void doBegin(Object transaction, TransactionDefinition definition) {}
    protected void doCommit(DefaultTransactionStatus status) { commits++; }
    protected void doRollback(DefaultTransactionStatus status) { rollbacks++; }
}
