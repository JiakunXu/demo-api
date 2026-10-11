package com.example.demo.weixin.manager;

import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.qq.weixin.mp.aes.AesException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class MessageCryptServiceImplTest {
    @Test
    void verifyDelegatesArgumentsAndResult() throws Exception {
        try (var crypts = mockConstruction(WXBizMsgCrypt.class, (client, context) -> {
            assertThat(context.arguments().toArray()).containsExactly("token", "key", "app");
            when(client.verifyUrl("signature", "timestamp", "nonce", "echo")).thenReturn("output");
        })) {
            assertThat(new MessageCryptServiceImpl().verify("token", "key", "app", "signature", "timestamp", "nonce", "echo")).isEqualTo("output");
            verify(crypts.constructed().getFirst()).verifyUrl("signature", "timestamp", "nonce", "echo");
        }
    }

    @Test
    void verifyPreservesSdkFailure() throws Exception {
        AesException failure = mock(AesException.class);
        try (var crypts = mockConstruction(WXBizMsgCrypt.class, (client, context) ->
                when(client.verifyUrl("signature", "timestamp", "nonce", "echo")).thenThrow(failure))) {
            assertThatThrownBy(() -> new MessageCryptServiceImpl().verify("token", "key", "app", "signature", "timestamp", "nonce", "echo")).hasCause(failure);
        }
    }

    @Test
    void decryptDelegatesArgumentsAndResult() throws Exception {
        try (var crypts = mockConstruction(WXBizMsgCrypt.class, (client, context) -> {
            assertThat(context.arguments().toArray()).containsExactly("token", "key", "app");
            when(client.decryptMsg("signature", "timestamp", "nonce", "data")).thenReturn("output");
        })) {
            assertThat(new MessageCryptServiceImpl().decrypt("token", "key", "app", "signature", "timestamp", "nonce", "data")).isEqualTo("output");
            verify(crypts.constructed().getFirst()).decryptMsg("signature", "timestamp", "nonce", "data");
        }
    }

    @Test
    void decryptPreservesSdkFailure() throws Exception {
        AesException failure = mock(AesException.class);
        try (var crypts = mockConstruction(WXBizMsgCrypt.class, (client, context) ->
                when(client.decryptMsg("signature", "timestamp", "nonce", "data")).thenThrow(failure))) {
            assertThatThrownBy(() -> new MessageCryptServiceImpl().decrypt("token", "key", "app", "signature", "timestamp", "nonce", "data")).hasCause(failure);
        }
    }

    @Test
    void encryptDelegatesArgumentsAndResult() throws Exception {
        try (var crypts = mockConstruction(WXBizMsgCrypt.class, (client, context) -> {
            assertThat(context.arguments().toArray()).containsExactly("token", "key", "app");
            when(client.encryptMsg("data", "timestamp", "nonce")).thenReturn("output");
        })) {
            assertThat(new MessageCryptServiceImpl().encrypt("token", "key", "app", "data", "timestamp", "nonce")).isEqualTo("output");
            verify(crypts.constructed().getFirst()).encryptMsg("data", "timestamp", "nonce");
        }
    }

    @Test
    void encryptPreservesSdkFailure() throws Exception {
        AesException failure = mock(AesException.class);
        try (var crypts = mockConstruction(WXBizMsgCrypt.class, (client, context) ->
                when(client.encryptMsg("data", "timestamp", "nonce")).thenThrow(failure))) {
            assertThatThrownBy(() -> new MessageCryptServiceImpl().encrypt("token", "key", "app", "data", "timestamp", "nonce")).hasCause(failure);
        }
    }

    @Test
    void rejectsInvalidKeyDuringInitialization() {
        assertThatThrownBy(() -> new MessageCryptServiceImpl().encrypt("token", "bad-key", "app", "data", "time", "nonce"))
            .hasCauseInstanceOf(AesException.class);
    }
}
