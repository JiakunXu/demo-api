package com.example.demo.bytedance.manager;

import com.example.demo.bytedance.api.MessageService;
import com.example.demo.bytedance.api.TokenService;
import com.example.demo.bytedance.api.bo.message.Result;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.mockito.MockedStatic;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.DynamicTest;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceImplTest {
    private MessageServiceImpl service() {
        var service = new MessageServiceImpl();
        ReflectionTestUtils.setField(service, "token", "token");
        ReflectionTestUtils.setField(service, "appId", "app");
        ReflectionTestUtils.setField(service, "appSecret", "secret");
        return service;
    }

    private String signature(String... values) throws Exception {
        Arrays.sort(values);
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-512").digest(String.join("", values).getBytes(java.nio.charset.StandardCharsets.UTF_8)));
    }

    @Test
    void verifiesIndependentDigestSignature() throws Exception {
        assertThat(service().verify(signature("token", "123", "nonce"), "123", "nonce", "echo")).isEqualTo("echo");
        assertThatThrownBy(() -> service().verify("wrong", "123", "nonce", "echo")).hasMessage("签名验证错误");
    }

    @Test
    void validatesRequiredCallbackArguments() {
        assertThatThrownBy(() -> service().verify("", "123", "nonce", "echo")).hasMessage("signature is null.");
        assertThatThrownBy(() -> service().verify("sign", "", "nonce", "echo")).hasMessage("timestamp is null.");
        assertThatThrownBy(() -> service().verify("sign", "123", "", "echo")).hasMessage("nonce is null.");
        assertThatThrownBy(() -> service().notify("sign", "123", "nonce", "")).hasMessage("data is null.");
    }

    @Test
    void signedNotificationRepliesToSender() throws Exception {
        var service = service();
        var tokens = mock(TokenService.class);
        ReflectionTestUtils.setField(service, "tokenService", tokens);
        when(tokens.getToken("app", "secret", "client_credential")).thenReturn("access");
        String data = "{\"FromUserName\":\"user\",\"Content\":\"hello\"}";
        try (MockedStatic<HttpUtil> http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.post(eq(MessageService.HTTPS_POST_URL + "access"), jsonBody("{\"open_id\":\"user\",\"msg_type\":\"text\",\"content\":\"感谢您的留言。\"}"))).thenReturn("{\"errno\":0}");
            assertThat(service.notify(signature("token", "123", "nonce", data), "123", "nonce", data).getContent()).isEqualTo("hello");
            verify(tokens).getToken("app", "secret", "client_credential");
            http.verify(() -> HttpUtil.post(anyString(), anyString()));
        }
    }

    @Test
    void notificationRemainsAvailableWhenAutomaticReplyFails() throws Exception {
        var service = service();
        var tokens = mock(TokenService.class);
        ReflectionTestUtils.setField(service, "tokenService", tokens);
        when(tokens.getToken(anyString(), anyString(), anyString())).thenThrow(new RuntimeException("token unavailable"));
        String data = "{\"FromUserName\":\"user\",\"Content\":\"hello\"}";
        assertThat(service.notify(signature("token", "123", "nonce", data), "123", "nonce", data).getFromUserName()).isEqualTo("user");
    }

    @TestFactory
    Stream<DynamicTest> sendsText() {
        return HttpContract.json("send", () -> new Endpoint(() -> HttpUtil.post(eq(MessageService.HTTPS_POST_URL + "access"), jsonBody("{\"open_id\":\"user\",\"msg_type\":\"text\",\"content\":\"hello\"}")), () -> service().send("access", "user", "hello"), Result.class, "{\"errno\":0,\"msg\":\"ok\"}", "{\"errno\":1,\"msg\":\"provider rejected\"}", "send_result is null."));
    }
}
