package com.example.demo.weixin.service;

import com.example.demo.weixin.api.TokenService;
import com.example.demo.weixin.api.JsapiTicketService;
import com.example.demo.weixin.api.bo.js.JsapiTicket;
import com.example.demo.cache.api.RedisService;
import com.example.demo.framework.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceImplTest {
    private final RedisService<String, String> redis = mock(RedisService.class);
    private final TokenService tokens = mock(TokenService.class);
    private final JsapiTicketService remote = mock(JsapiTicketService.class);
    private final TicketServiceImpl service = new TicketServiceImpl();
    private final String key = RedisService.CACHE_KEY_WX_TICKET + "app&secret";
    @BeforeEach
    void initialize() {
        ReflectionTestUtils.setField(service, "redisService", redis);
        ReflectionTestUtils.setField(service, "tokenService", tokens);
        ReflectionTestUtils.setField(service, "jsapiTicketService", remote);
    }

    @Test
    void cachedTicketSkipsTokenAndProvider() {
        when(redis.get(key)).thenReturn("cached");
        assertThat(service.getTicket("app", "secret")).isEqualTo("cached");
        verifyNoInteractions(tokens, remote);
    }

    @Test
    void fetchUsesAccessTokenAndSubtractsFiveMinutesFromLifetime() {
        when(tokens.getToken("client_credential", "app", "secret")).thenReturn("token");
        JsapiTicket response = new JsapiTicket(); response.setTicket("ticket"); response.setExpiresIn(7200);
        when(remote.getJsapiTicket("token")).thenReturn(response);
        assertThat(service.getTicket("app", "secret")).isEqualTo("ticket");
        verify(redis).set(key, "ticket", 6900L);
    }

    @Test
    void redisReadAndWriteFailureDoNotDiscardTicket() {
        when(redis.get(key)).thenThrow(new ServiceException(503, "offline"));
        when(tokens.getToken("client_credential", "app", "secret")).thenReturn("token");
        JsapiTicket response = new JsapiTicket(); response.setTicket("ticket"); response.setExpiresIn(7200);
        when(remote.getJsapiTicket("token")).thenReturn(response);
        doThrow(new ServiceException(503, "offline")).when(redis).set(key, "ticket", 6900L);
        assertThat(service.getTicket("app", "secret")).isEqualTo("ticket");
    }

    @Test
    void tokenFailurePreventsTicketCall() {
        when(tokens.getToken(anyString(), anyString(), anyString())).thenThrow(new RuntimeException("token failed"));
        assertThatThrownBy(() -> service.getTicket("app", "secret")).hasMessage("token failed");
        verifyNoInteractions(remote);
    }

}
