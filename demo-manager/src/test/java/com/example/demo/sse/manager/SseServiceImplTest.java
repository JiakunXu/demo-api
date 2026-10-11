package com.example.demo.sse.manager;

import com.example.demo.tunnel.api.TunnelService;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.test.util.ReflectionTestUtils;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.UUID;
import java.io.IOException;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class SseServiceImplTest {
    @ParameterizedTest
    @ValueSource(longs = { 0, 5000 })
    void registersEmitterAndCleansUpOnEveryLifecycleEvent(long timeout) {
        String key = UUID.randomUUID().toString();
        TunnelService tunnels = mock(TunnelService.class);
        SseServiceImpl service = new SseServiceImpl();
        ReflectionTestUtils.setField(service, "tunnelService", tunnels);
        try (var emitters = mockConstruction(SseEmitter.class)) {
            SseEmitter emitter = (SseEmitter) (timeout == 0 ? service.init(key) : service.init(key, timeout));
            assertThat(SseManager.get(key)).isSameAs(emitter);
            ArgumentCaptor<Runnable> completion = ArgumentCaptor.forClass(Runnable.class);
            ArgumentCaptor<Runnable> expired = ArgumentCaptor.forClass(Runnable.class);
            @SuppressWarnings("unchecked") ArgumentCaptor<Consumer<Throwable>> error = ArgumentCaptor.forClass(Consumer.class);
            verify(emitter).onCompletion(completion.capture());
            verify(emitter).onTimeout(expired.capture());
            verify(emitter).onError(error.capture());
            completion.getValue().run();
            assertThat(SseManager.get(key)).isNull();
            SseManager.put(key, emitter);
            expired.getValue().run();
            assertThat(SseManager.get(key)).isNull();
            SseManager.put(key, emitter);
            error.getValue().accept(new IOException("disconnected"));
            assertThat(SseManager.get(key)).isNull();
            verify(tunnels, times(3)).deleteTunnel(key, "sys");
        } finally { SseManager.remove(key); }
    }

    @Test
    void sendsAndCompletesExistingEmitter() throws IOException {
        String key = UUID.randomUUID().toString();
        SseEmitter emitter = mock(SseEmitter.class);
        SseManager.put(key, emitter);
        try {
            SseServiceImpl service = new SseServiceImpl();
            service.send(key, "hello");
            service.complete(key);
            IOException error = new IOException("failed");
            service.completeWithError(key, error);
            verify(emitter).send("hello");
            verify(emitter).complete();
            verify(emitter).completeWithError(error);
        } finally { SseManager.remove(key); }
    }

    @Test
    void convertsSendFailureToEmitterError() throws IOException {
        String key = UUID.randomUUID().toString();
        SseEmitter emitter = mock(SseEmitter.class);
        IOException failure = new IOException("failed");
        doThrow(failure).when(emitter).send("hello");
        SseManager.put(key, emitter);
        try {
            new SseServiceImpl().send(key, "hello");
            verify(emitter).completeWithError(failure);
        } finally { SseManager.remove(key); }
    }

    @Test
    void missingTunnelIsSafe() {
        String key = UUID.randomUUID().toString();
        SseServiceImpl service = new SseServiceImpl();
        assertThatCode(() -> {
            service.send(key, "hello");
            service.complete(key);
            service.completeWithError(key, new IOException());
        }).doesNotThrowAnyException();
    }
}
