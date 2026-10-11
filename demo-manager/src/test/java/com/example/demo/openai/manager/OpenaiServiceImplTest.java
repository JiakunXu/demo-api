package com.example.demo.openai.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.sse.api.SseService;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import org.mockito.ArgumentCaptor;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class OpenaiServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void forwardsStreamAndCompletesTunnel() {
        WebClient client = mock(WebClient.class);
        WebClient.RequestBodyUriSpec request = mock(WebClient.RequestBodyUriSpec.class, RETURNS_SELF);
        WebClient.RequestHeadersSpec headers = mock(WebClient.RequestHeadersSpec.class, RETURNS_SELF);
        WebClient.ResponseSpec response = mock(WebClient.ResponseSpec.class);
        when(client.post()).thenReturn(request);
        doReturn(headers).when(request).bodyValue(any());
        when(headers.retrieve()).thenReturn(response);
        when(response.bodyToFlux(String.class)).thenReturn(Flux.just("first", "second"));
        SseService sse = mock(SseService.class);
        OpenaiServiceImpl service = new OpenaiServiceImpl();
        ReflectionTestUtils.setField(service, "sseService", sse);
        try (var factory = mockStatic(WebClient.class)) {
            factory.when(() -> WebClient.create("https://api.openai.com")).thenReturn(client);
            service.chat("tunnel", List.of());
            verify(request).uri("/v1/chat/completions");
            verify(request).contentType(MediaType.APPLICATION_JSON);
            ArgumentCaptor<Object> body = ArgumentCaptor.forClass(Object.class);
            verify(request).bodyValue(body.capture());
            assertThat(JSON.parseObject(body.getValue().toString()).getBoolean("stream")).isTrue();
            assertThat(JSON.parseObject(body.getValue().toString()).getJSONArray("messages")).isEmpty();
            var order = inOrder(sse);
            order.verify(sse).send("tunnel", "first");
            order.verify(sse).send("tunnel", "second");
            order.verify(sse).complete("tunnel");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void streamFailureCompletesTunnelAndPropagates() {
        WebClient client = mock(WebClient.class);
        WebClient.RequestBodyUriSpec request = mock(WebClient.RequestBodyUriSpec.class, RETURNS_SELF);
        WebClient.RequestHeadersSpec headers = mock(WebClient.RequestHeadersSpec.class, RETURNS_SELF);
        WebClient.ResponseSpec response = mock(WebClient.ResponseSpec.class);
        when(client.post()).thenReturn(request);
        doReturn(headers).when(request).bodyValue(any());
        when(headers.retrieve()).thenReturn(response);
        RuntimeException failure = new RuntimeException("stream failed");
        when(response.bodyToFlux(String.class)).thenReturn(Flux.error(failure));
        SseService sse = mock(SseService.class);
        OpenaiServiceImpl service = new OpenaiServiceImpl();
        ReflectionTestUtils.setField(service, "sseService", sse);
        try (var factory = mockStatic(WebClient.class)) {
            factory.when(() -> WebClient.create("https://api.openai.com")).thenReturn(client);
            assertThatThrownBy(() -> service.chat("tunnel", List.of())).isSameAs(failure);
            verify(sse).complete("tunnel");
        }
    }
}
