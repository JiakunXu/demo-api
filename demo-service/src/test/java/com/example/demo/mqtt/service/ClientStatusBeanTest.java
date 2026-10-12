package com.example.demo.mqtt.service;

import com.alibaba.mqtt.server.ServerConsumer;
import com.alibaba.mqtt.server.config.ChannelConfig;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientStatusBeanTest {
    private final ClientStatusBean bean = new ClientStatusBean();
    private final ClientStatusListener listener = mock(ClientStatusListener.class);
    @BeforeEach
    void initialize() {
        for (String field : new String[] {"domain", "instanceId", "accessKey", "secretKey", "groupId"}) ReflectionTestUtils.setField(bean, field, field);
        ReflectionTestUtils.setField(bean, "port", 5672);
    }

    @Test
    void configurationStartsAndSubscribesUsingCorrectChannel() throws Exception {
        try (var consumers = mockConstruction(ServerConsumer.class, (consumer, context) -> {
            ChannelConfig config = (ChannelConfig) context.arguments().getFirst();
            assertThat(config.getDomain()).isEqualTo("domain"); assertThat(config.getPort()).isEqualTo(5672);
            assertThat(config.getInstanceId()).isEqualTo("instanceId");
            assertThat(config.getAccessKey()).isEqualTo("accessKey");
            assertThat(config.getSecretKey()).isEqualTo("secretKey");
        })) {
            assertThat(bean.clientStatus(listener)).isSameAs(consumers.constructed().getFirst());
            var order = inOrder(consumers.constructed().getFirst());
            order.verify(consumers.constructed().getFirst()).start();
            order.verify(consumers.constructed().getFirst()).subscribeStatus("groupId", listener);
        }
    }

    @Test
    void connectionFailurePreventsSubscriptionAndPropagates() throws Exception {
        IOException failure = new IOException("connection refused");
        try (var consumers = mockConstruction(ServerConsumer.class, (consumer, context) -> doThrow(failure).when(consumer).start())) {
            assertThatThrownBy(() -> bean.clientStatus(listener)).isSameAs(failure);
            verify(consumers.constructed().getFirst(), never()).subscribeStatus(anyString(), any(ClientStatusListener.class));
        }
    }
}
