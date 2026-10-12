package com.example.demo.mqtt.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.alibaba.mqtt.server.model.MessageProperties;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsumerListenerTest {
    @Test
    void diagnosticsDecodePayloadAsUtf8() {
        Logger logger = (Logger) LoggerFactory.getLogger(ConsumerListener.class);
        ListAppender<ILoggingEvent> logs = new ListAppender<>(); logs.start(); logger.addAppender(logs);
        try {
            new ConsumerListener().process("message-id", mock(MessageProperties.class), "消息内容".getBytes(StandardCharsets.UTF_8));
            assertThat(logs.list).hasSize(1);
            assertThat(logs.list.getFirst().getFormattedMessage()).contains("message-id", "消息内容");
        } finally { logger.detachAppender(logs); logs.stop(); }
    }
}
