package com.example.demo.weixin.service;

import com.example.demo.weixin.api.AccessTokenService;
import com.example.demo.weixin.api.bo.token.AccessToken;
import com.example.demo.cache.api.RedisService;
import com.example.demo.framework.exception.ServiceException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceImplTest {
    private final RedisService<String, String> redis = mock(RedisService.class);
    private final AccessTokenService remote = mock(AccessTokenService.class);
    private final TokenServiceImpl service = new TokenServiceImpl();
    private final String key = RedisService.CACHE_KEY_WX_TOKEN + "client_credential&app&secret";
    @BeforeEach
    void initialize() {
        ReflectionTestUtils.setField(service, "redisService", redis);
        ReflectionTestUtils.setField(service, "accessTokenService", remote);
    }

    @Test
    void cacheHitAvoidsProviderCall() {
        when(redis.get(key)).thenReturn("cached");
        assertThat(service.getToken("client_credential", "app", "secret")).isEqualTo("cached");
        verifyNoInteractions(remote);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void missingCachedTokenFetchesProviderAndUsesEarlyExpiration(String cached) {
        when(redis.get(key)).thenReturn(cached);
        AccessToken response = new AccessToken(); response.setAccessToken("fresh"); response.setExpiresIn(7200);
        when(remote.getAccessToken("client_credential", "app", "secret")).thenReturn(response);
        assertThat(service.getToken("client_credential", "app", "secret")).isEqualTo("fresh");
        verify(redis).set(key, "fresh", 6900L);
    }

    @Test
    void cacheFailuresStillAllowProviderTokenToReturn() {
        when(redis.get(key)).thenThrow(new ServiceException(503, "offline"));
        AccessToken response = new AccessToken(); response.setAccessToken("fresh"); response.setExpiresIn(7200);
        when(remote.getAccessToken("client_credential", "app", "secret")).thenReturn(response);
        doThrow(new ServiceException(503, "offline")).when(redis).set(key, "fresh", 6900L);
        assertThat(service.getToken("client_credential", "app", "secret")).isEqualTo("fresh");
    }

    @Test
    void providerFailureDoesNotCacheToken() {
        RuntimeException failure = new RuntimeException("provider unavailable");
        when(remote.getAccessToken("client_credential", "app", "secret")).thenThrow(failure);
        assertThatThrownBy(() -> service.getToken("client_credential", "app", "secret")).isSameAs(failure);
        verify(redis, never()).set(anyString(), anyString(), anyLong());
    }
}
