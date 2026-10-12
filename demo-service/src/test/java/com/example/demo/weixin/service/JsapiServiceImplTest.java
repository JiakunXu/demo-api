package com.example.demo.weixin.service;

import com.example.demo.weixin.api.TicketService;
import com.example.demo.framework.util.EncryptUtil;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsapiServiceImplTest {
    private final TicketService tickets = mock(TicketService.class);
    private final JsapiServiceImpl service = new JsapiServiceImpl();
    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(service, "ticketService", tickets);
    ReflectionTestUtils.setField(service, "appId", "app");
    ReflectionTestUtils.setField(service, "appSecret", "secret");
    }

    @Test
    void signatureInputUsesTicketNonceTimestampAndExactUrl() {
        when(tickets.getTicket("app", "secret")).thenReturn("ticket");
        try (var encryption = mockStatic(EncryptUtil.class)) {
            encryption.when(() -> EncryptUtil.encryptSha(anyString())).thenReturn("signature");
            var result = service.getJsapi("https://example.com/path?a=1");
            assertThat(result.getAppId()).isEqualTo("app"); assertThat(result.getSignature()).isEqualTo("signature");
            assertThat(result.getNonceStr()).matches("[0-9a-f-]{36}");
            assertThat(result.getTimestamp()).matches("[0-9]+");
            encryption.verify(() -> EncryptUtil.encryptSha("jsapi_ticket=ticket&noncestr=" + result.getNonceStr() + "&timestamp=" + result.getTimestamp() + "&url=https://example.com/path?a=1"));
        }
    }

    @Test
    void blankUrlIsRejectedAndSigningFailurePropagates() {
        assertThatThrownBy(() -> service.getJsapi(" ")).hasMessage("url cannot be null.");
        verifyNoInteractions(tickets);
        when(tickets.getTicket(anyString(), anyString())).thenReturn("ticket");
        try (var encryption = mockStatic(EncryptUtil.class)) {
            encryption.when(() -> EncryptUtil.encryptSha(anyString())).thenThrow(new IOException("sign failed"));
            assertThatThrownBy(() -> service.getJsapi("https://example.com")).hasMessage("sign failed");
        }
    }
}
