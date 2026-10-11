package com.example.demo.sse.manager;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class SseManagerTest {
    @Test
    void tracksReplacementsAndRemovesOnlyRegisteredKey() {
        String key = UUID.randomUUID().toString();
        SseEmitter first = mock(SseEmitter.class);
        SseEmitter second = mock(SseEmitter.class);
        try {
            assertThat(SseManager.get(key)).isNull();
            assertThat(SseManager.put(key, first)).isNull();
            assertThat(SseManager.get(key)).isSameAs(first);
            assertThat(SseManager.put(key, second)).isSameAs(first);
            assertThat(SseManager.remove(key)).isSameAs(second);
            assertThat(SseManager.get(key)).isNull();
            assertThat(SseManager.remove(key)).isNull();
        } finally { SseManager.remove(key); }
    }
}
