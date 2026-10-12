package com.example.demo.chat.service;

import com.example.demo.subscribe.api.SubscribeService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigInteger;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatStatusServiceImplTest {
    private final SubscribeService subscriptions = mock(SubscribeService.class);
    private final ChatStatusServiceImpl service = new ChatStatusServiceImpl();
    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(service, "subscribeService", subscriptions); }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 3000000000L})
    void presenceDependsOnFriendsSubscription(long count) {
        when(subscriptions.countSubscribe(BigInteger.ONE, "app", "1")).thenReturn(count);
        assertThat(service.getStatus(BigInteger.TEN, "1")).isEqualTo(count > 0 ? "online" : "busy");
        verify(subscriptions).countSubscribe(BigInteger.ONE, "app", "1");
    }

    @Test
    void missingUserOrFriendIsBusyWithoutLookingUpPresence() {
        assertThat(service.getStatus(null, "1")).isEqualTo("busy");
        assertThat(service.getStatus(BigInteger.TEN, " ")).isEqualTo("busy");
        verifyNoInteractions(subscriptions);
    }
}
