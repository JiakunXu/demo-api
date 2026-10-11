package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MessageCryptService;
import com.example.demo.weixin.api.MessageService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.*;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.DynamicTest;
import org.springframework.test.util.ReflectionTestUtils;
import java.text.MessageFormat;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceImplTest {
    @Test
    void delegatesCallbackCryptography() {
        var crypt = mock(MessageCryptService.class);
        var service = new MessageServiceImpl();
        ReflectionTestUtils.setField(service, "messageCryptService", crypt);
        ReflectionTestUtils.setField(service, "token", "token");
        ReflectionTestUtils.setField(service, "encodingAesKey", "key");
        ReflectionTestUtils.setField(service, "appId", "app");
        when(crypt.verify("token", "key", "app", "sign", "timestamp", "nonce", "echo")).thenReturn("verified");
        when(crypt.decrypt("token", "key", "app", "sign", "timestamp", "nonce", "cipher")).thenReturn("plain");
        assertThat(service.verify("sign", "timestamp", "nonce", "echo")).isEqualTo("verified");
        assertThat(service.notify("sign", "timestamp", "nonce", "cipher")).isEqualTo("plain");
        verify(crypt).verify("token", "key", "app", "sign", "timestamp", "nonce", "echo");
        verify(crypt).decrypt("token", "key", "app", "sign", "timestamp", "nonce", "cipher");
    }

    @TestFactory
    Stream<DynamicTest> sendsTemplates() {
        return HttpContract.json("template", () -> {
            var service = new MessageServiceImpl();
            Template payload = JSON.parseObject("{\"template_id\":\"template\",\"url\":\"https://example.com\"}", Template.class);
            return new Endpoint(() -> HttpUtil.post(eq(MessageFormat.format(MessageService.HTTPS_TEMPLATE_URL, "token")), jsonBody("{\"touser\":\"user\",\"template_id\":\"template\",\"url\":\"https://example.com\"}")), () -> service.send("token", "user", payload), Result.class, "{\"errcode\":0,\"msgid\":123}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsSubscriptions() {
        return HttpContract.json("subscribe", () -> {
            var service = new MessageServiceImpl();
            Subscribe payload = JSON.parseObject("{\"template_id\":\"template\",\"page\":\"pages/index\"}", Subscribe.class);
            return new Endpoint(() -> HttpUtil.post(eq(MessageFormat.format(MessageService.HTTPS_SUBSCRIBE_URL, "token")), jsonBody("{\"touser\":\"user\",\"template_id\":\"template\",\"page\":\"pages/index\"}")), () -> service.send("token", "user", payload), BaseResult.class, "{\"errcode\":0}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsUniformMessages() {
        return HttpContract.json("uniform", () -> {
            var service = new MessageServiceImpl();
            Uniform payload = JSON.parseObject("{\"mp_template_msg\":{\"appid\":\"app\",\"template_id\":\"template\"}}", Uniform.class);
            return new Endpoint(() -> HttpUtil.post(eq(MessageFormat.format(MessageService.HTTPS_UNIFORM_URL, "token")), jsonBody("{\"touser\":\"user\",\"mp_template_msg\":{\"appid\":\"app\",\"template_id\":\"template\"}}")), () -> service.send("token", "user", payload), BaseResult.class, "{\"errcode\":0}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }
}
