package com.example.demo.mq.service;

import com.example.demo.socket.api.WebSocketService;
import org.apache.rocketmq.common.message.MessageExt;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsumerBroadcastListenerTest {
    private final WebSocketService socket = mock(WebSocketService.class);
    private final ConsumerBroadcastListener listener = new ConsumerBroadcastListener();
    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(listener, "webSocketService", socket); }
    private MessageExt message(String tag) { MessageExt message = new MessageExt();
    message.setTags(tag);
    message.setKeys("tunnel");
    message.setBody("消息".getBytes(StandardCharsets.UTF_8));
    return message;
    }

    @Test
    void socketMessageRoutesPayloadToRequestedTunnel() { listener.onMessage(message("web.socket"));
    verify(socket).sendMessage("tunnel", "消息");
    }

    @Test
    void unknownTagIsIgnored() { listener.onMessage(message("other")); verifyNoInteractions(socket); }
    @Test
    void socketFailureDoesNotPropagateToBroadcastConsumer() {
        doThrow(new RuntimeException("socket closed")).when(socket).sendMessage("tunnel", "消息");
        assertThatCode(() -> listener.onMessage(message("web.socket"))).doesNotThrowAnyException();
    }
}
