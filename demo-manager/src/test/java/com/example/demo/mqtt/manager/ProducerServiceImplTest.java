package com.example.demo.mqtt.manager;

import com.alibaba.mqtt.server.ServerProducer;
import com.alibaba.mqtt.server.common.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class ProducerServiceImplTest {
    @Test
    void sendsExactTopicAndPayload() throws Exception {
        ServerProducer client = mock(ServerProducer.class);
        SendResult response = mock(SendResult.class);
        byte[] body = { 1, 2, 3 };
        when(client.sendMessage("topic", body)).thenReturn(response);
        when(response.getMsgId()).thenReturn("message-id");
        ProducerServiceImpl service = new ProducerServiceImpl();
        ReflectionTestUtils.setField(service, "producer", client);
        assertThat(service.sendMessage("topic", body)).isEqualTo("message-id");
        verify(client).sendMessage("topic", body);
    }

    @Test
    void preservesTransportFailure() throws Exception {
        ServerProducer client = mock(ServerProducer.class);
        IOException failure = new IOException("disconnected");
        when(client.sendMessage(anyString(), any(byte[].class))).thenThrow(failure);
        ProducerServiceImpl service = new ProducerServiceImpl();
        ReflectionTestUtils.setField(service, "producer", client);
        assertThatThrownBy(() -> service.sendMessage("topic", new byte[] { 1 })).hasCause(failure);
    }
}
