package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.bytedance.api.UserStorageService;
import com.example.demo.bytedance.api.bo.user.UserStorage;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.framework.util.EncryptUtil;
import org.mockito.MockedStatic;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import java.util.List;
import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserStorageServiceImplTest {
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void signsAndSendsStorageMutation(boolean remove) throws Exception {
        var item = new UserStorage.KvItem();
        item.setKey("score");
        item.setValue("99");
        String endpoint = remove ? UserStorageService.HTTPS_REMOVE_URL : UserStorageService.HTTPS_SET_URL;
        try (MockedStatic<EncryptUtil> encryption = mockStatic(EncryptUtil.class);
             MockedStatic<HttpUtil> http = mockStatic(HttpUtil.class)) {
            encryption.when(() -> EncryptUtil.encryptHmac(anyString(), eq("session_key"))).thenAnswer(invocation -> {
                var unsigned = JSON.parseObject((String) invocation.getArgument(0));
                assertThat(unsigned.getString("access_token")).isEqualTo("token");
                assertThat(unsigned.getString("openid")).isEqualTo("user");
                assertThat(unsigned.getString("sig_method")).isEqualTo("hmac_sha256");
                assertThat(unsigned.getJSONArray("kv_list").getJSONObject(0).getString("value")).isEqualTo("99");
                assertThat(unsigned.containsKey("signature")).isFalse();
                return "signature";
            });
            http.when(() -> HttpUtil.download(eq(endpoint), anyString())).thenAnswer(invocation -> {
                var signed = JSON.parseObject((String) invocation.getArgument(1));
                assertThat(signed.getString("signature")).isEqualTo("signature");
                assertThat(signed.getJSONArray("kv_list").getJSONObject(0).getString("key")).isEqualTo("score");
                return "{\"errcode\":0,\"message\":\"ok\"}".getBytes(StandardCharsets.UTF_8);
            });
            var service = new UserStorageServiceImpl();
            var result = remove ? service.remove("token", "user", List.of(item)) : service.set("token", "user", List.of(item));
            assertThat(result.getMessage()).isEqualTo("ok");
            encryption.verify(() -> EncryptUtil.encryptHmac(anyString(), eq("session_key")));
            http.verify(() -> HttpUtil.download(eq(endpoint), anyString()));
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void signatureFailurePreventsHttpRequest(boolean remove) throws Exception {
        var failure = new IOException("sign failed");
        try (MockedStatic<EncryptUtil> encryption = mockStatic(EncryptUtil.class);
             MockedStatic<HttpUtil> http = mockStatic(HttpUtil.class)) {
            encryption.when(() -> EncryptUtil.encryptHmac(anyString(), anyString())).thenThrow(failure);
            assertThatThrownBy(() -> {
                if (remove) new UserStorageServiceImpl().remove("token", "user", List.of());
                else new UserStorageServiceImpl().set("token", "user", List.of());
            }).hasCause(failure);
            http.verifyNoInteractions();
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void transportFailurePreservesCause(boolean remove) throws Exception {
        var failure = new RuntimeException("network");
        try (MockedStatic<EncryptUtil> encryption = mockStatic(EncryptUtil.class);
             MockedStatic<HttpUtil> http = mockStatic(HttpUtil.class)) {
            encryption.when(() -> EncryptUtil.encryptHmac(anyString(), anyString())).thenReturn("signature");
            http.when(() -> HttpUtil.download(anyString(), anyString())).thenThrow(failure);
            assertThatThrownBy(() -> {
                if (remove) new UserStorageServiceImpl().remove("token", "user", List.of());
                else new UserStorageServiceImpl().set("token", "user", List.of());
            }).hasCause(failure);
        }
    }
}
