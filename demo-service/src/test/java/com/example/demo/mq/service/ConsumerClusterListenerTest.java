package com.example.demo.mq.service;

import com.example.demo.chat.api.ChatService;
import com.example.demo.subscribe.api.SubscribeService;
import org.apache.rocketmq.common.message.MessageExt;
import org.mockito.ArgumentCaptor;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigInteger;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsumerClusterListenerTest {
    private final ChatService chats = mock(ChatService.class);
    private final SubscribeService subscriptions = mock(SubscribeService.class);
    private final ConsumerClusterListener listener = new ConsumerClusterListener();
    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(listener, "chatService", chats);
    ReflectionTestUtils.setField(listener, "subscribeService", subscriptions);
    }
    private MessageExt message(String tag, String body) { MessageExt message = new MessageExt();
    message.setTags(tag);
    message.setKeys("10");
    message.setBody(body.getBytes(StandardCharsets.UTF_8));
    return message;
    }

    @Test
    void chatMessagePublishesFriendSpecificEventToRecipientScene() {
        String body = "{\"userId\":10,\"friendId\":1,\"content\":\"你好\"}";
        listener.onMessage(message("chat.message", body));
        var capture = ArgumentCaptor.forClass(com.example.demo.socket.api.bo.Message.class);
        verify(subscriptions).sendMessage(eq("app"), eq("10"), capture.capture());
        assertThat(capture.getValue().getType()).isEqualTo("chat.1.message");
        assertThat(capture.getValue().getContent()).isEqualTo(body);
        verifyNoInteractions(chats);
    }

    @Test
    void chatUpdatePassesParticipantIdentifiersAndUnreadCount() {
        listener.onMessage(message("chat.update", "{\"userId\":10,\"friendId\":1,\"unread\":1}"));
        verify(chats).saveOrUpdate(eq(BigInteger.TEN), eq(BigInteger.ONE), argThat(chat -> chat.getUnread() == 1));
        verifyNoInteractions(subscriptions);
    }

    @Test
    void unknownTagIsIgnoredAndMalformedKnownPayloadIsRejected() {
        listener.onMessage(message("other", "not-json")); verifyNoInteractions(chats, subscriptions);
        assertThatThrownBy(() -> listener.onMessage(message("chat.update", "not-json"))).isInstanceOf(com.alibaba.fastjson2.JSONException.class);
    }

    @Test
    void businessUpdateFailurePropagatesForConsumerRetry() {
        when(chats.saveOrUpdate(any(), any(), any())).thenThrow(new RuntimeException("database failed"));
        assertThatThrownBy(() -> listener.onMessage(message("chat.update", "{\"userId\":10,\"friendId\":1}"))).hasMessage("database failed");
    }
}
