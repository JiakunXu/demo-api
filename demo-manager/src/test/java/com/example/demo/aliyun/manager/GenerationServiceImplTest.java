package com.example.demo.aliyun.manager;

import com.alibaba.dashscope.aigc.generation.*;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.example.demo.sse.api.SseService;
import org.mockito.MockedConstruction;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerationServiceImplTest {
    @Test
    @SuppressWarnings("unchecked")
    void streamingCallbacksDeliverEventsAndCloseTunnel() throws Exception {
        SseService sse = mock(SseService.class);
        var service = new GenerationServiceImpl();
        ReflectionTestUtils.setField(service, "sseService", sse);
        try (MockedConstruction<Generation> clients = mockConstruction(Generation.class)) {
            service.streamCall("tunnel", "model", "hello");
            var param = ArgumentCaptor.forClass(GenerationParam.class);
            var callback = ArgumentCaptor.forClass(ResultCallback.class);
            verify(clients.constructed().getFirst()).streamCall(param.capture(), callback.capture());
            assertThat(param.getValue().getModel()).isEqualTo("model");
            assertThat(param.getValue().getMessages()).hasSize(1);
            assertThat(param.getValue().getMessages().getFirst().getRole()).isEqualTo("user");
            assertThat(param.getValue().getMessages().getFirst().getContent()).isEqualTo("hello");
            assertThat(param.getValue().getIncrementalOutput()).isTrue();
            GenerationResult event = mock(GenerationResult.class);
            when(event.toString()).thenReturn("chunk");
            callback.getValue().onEvent(event);
            verify(sse).send("tunnel", "chunk");
            callback.getValue().onComplete();
            verify(sse).complete("tunnel");
            reset(sse);
            callback.getValue().onError(new RuntimeException("upstream"));
            verify(sse).complete("tunnel");
        }
    }

    @Test
    void sdkFailurePreservesCause() throws Exception {
        var failure = new NoApiKeyException();
        try (MockedConstruction<Generation> clients = mockConstruction(Generation.class, (client, context) -> doThrow(failure).when(client).streamCall(any(GenerationParam.class), any(ResultCallback.class)))) {
            assertThatThrownBy(() -> new GenerationServiceImpl().streamCall("tunnel", "model", "hello")).hasCause(failure);
        }
    }
}
