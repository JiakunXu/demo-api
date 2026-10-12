package com.example.demo.weixin.service;

import com.example.demo.weixin.api.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeixinNotifyServiceImplTest {
    private final MessageService messages = mock(MessageService.class);
    private final WeixinNotifyServiceImpl service = new WeixinNotifyServiceImpl();
    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(service, "messageService", messages); }
    @Test
    void verificationPassesArgumentsAndReturnsEcho() {
        when(messages.verify("sign", "time", "nonce", "echo")).thenReturn("verified");
        assertThat(service.verify("sign", "time", "nonce", "echo")).isEqualTo("verified");
        verify(messages).verify("sign", "time", "nonce", "echo");
    }

    @Test
    void notificationDelegatesThenReturnsEmptyAcknowledgment() {
        assertThat(service.notify("sign", "time", "nonce", "data")).isEmpty();
        verify(messages).notify("sign", "time", "nonce", "data");
    }

    @Test
    void invalidNotificationPropagatesFailure() {
        when(messages.notify(anyString(), anyString(), anyString(), anyString())).thenThrow(new RuntimeException("invalid signature"));
        assertThatThrownBy(() -> service.notify("sign", "time", "nonce", "data")).hasMessage("invalid signature");
    }
}
