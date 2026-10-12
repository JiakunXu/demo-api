package com.example.demo.security.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.security.api.bo.LoginUser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigInteger;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefreshTokenServiceImplTest {
    private final RedisService<String, Boolean> redis = mock(RedisService.class);
    private final RefreshTokenServiceImpl service = new RefreshTokenServiceImpl();
    private final LoginUser user = new LoginUser();
    private final String key = RedisService.CACHE_KEY_REFRESH_TOKEN + "10@revision";

    @BeforeEach
    void initialize() {
        ReflectionTestUtils.setField(service, "redisService", redis);
        user.setId(BigInteger.TEN);
        user.setRefreshToken("revision");
    }

    @Test
    void nullUserDoesNotAccessRedis() {
        assertThat(service.validate(null)).isFalse();
        service.set(null);
        service.remove(null);
        verifyNoInteractions(redis);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void validatesRevisionSpecificCacheKey(boolean exists) {
        when(redis.hasKey(key)).thenReturn(exists);
        assertThat(service.validate(user)).isEqualTo(exists);
        verify(redis).hasKey(key);
    }

    @Test
    void writesConfiguredLifetimeAndRemovesTheSameRevision() {
        service.set(user);
        service.remove(user);
        verify(redis).set(key, true, RedisService.CACHE_KEY_REFRESH_TOKEN_DEFAULT_EXP);
        verify(redis).remove(key);
    }

    @Test
    void writeAndRemoveFailuresAreBestEffort() {
        doThrow(new RuntimeException("offline")).when(redis).set(key, true, RedisService.CACHE_KEY_REFRESH_TOKEN_DEFAULT_EXP);
        doThrow(new RuntimeException("offline")).when(redis).remove(key);
        assertThatCode(() -> service.set(user)).doesNotThrowAnyException();
        assertThatCode(() -> service.remove(user)).doesNotThrowAnyException();
    }
}
