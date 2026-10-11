package com.example.demo.mqtt.manager;

import com.alibaba.mqtt.server.ServerProducer;
import com.alibaba.mqtt.server.config.ChannelConfig;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class ProducerBeanTest {
    @Test
    void createsProducerWithConfiguredChannel() {
        ProducerBean bean = new ProducerBean();
        ReflectionTestUtils.setField(bean, "domain", "mqtt.test");
        ReflectionTestUtils.setField(bean, "port", 1883);
        ReflectionTestUtils.setField(bean, "instanceId", "instance");
        ReflectionTestUtils.setField(bean, "accessKey", "test-key");
        ReflectionTestUtils.setField(bean, "secretKey", "test-secret");
        AtomicReference<ChannelConfig> captured = new AtomicReference<>();
        try (var clients = mockConstruction(ServerProducer.class, (client, context) ->
                captured.set((ChannelConfig) context.arguments().getFirst()))) {
            assertThat(bean.serverProducer()).isSameAs(clients.constructed().getFirst());
            assertThat(captured.get().getDomain()).isEqualTo("mqtt.test");
            assertThat(captured.get().getPort()).isEqualTo(1883);
            assertThat(captured.get().getInstanceId()).isEqualTo("instance");
            assertThat(captured.get().getAccessKey()).isEqualTo("test-key");
            assertThat(captured.get().getSecretKey()).isEqualTo("test-secret");
            verifyNoInteractions(clients.constructed().getFirst());
        }
    }
}
