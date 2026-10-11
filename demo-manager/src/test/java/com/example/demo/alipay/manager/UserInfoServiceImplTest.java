package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.example.demo.alipay.api.bo.user.UserInfo;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserInfoServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void getUserInfo0BuildsRequestAndReturnsResult() throws Exception {
        String authToken = "authToken-value";
        UserInfoServiceImpl target = new UserInfoServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        response.httpBody = "{\"alipay_user_info_share_response\": {\"code\": \"10000\", \"user_id\": \"user-1\", \"access_token\": \"token-1\", \"order_id\": \"order-1\", \"out_biz_no\": \"business-1\"}}";
        java.util.Map<String, Object> expectedText = new java.util.HashMap<>();
        expectedText.put("auth_token", authToken);

        when(client.execute(eq("alipay.user.info.share"), anyMap(), isNull())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.getUserInfo(authToken)).usingRecursiveComparison().isEqualTo(JSON.parseObject("{\"code\":\"10000\",\"user_id\":\"user-1\",\"access_token\":\"token-1\",\"order_id\":\"order-1\",\"out_biz_no\":\"business-1\"}", UserInfo.class));
            ArgumentCaptor<java.util.Map> text = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("alipay.user.info.share"), text.capture(), isNull());
            assertThat(text.getValue()).isEqualTo(expectedText);

        }
    }

    @Test
    void getUserInfo0PreservesSdkFailure() throws Exception {
        String authToken = "authToken-value";
        UserInfoServiceImpl target = new UserInfoServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("alipay.user.info.share"), anyMap(), isNull())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.getUserInfo(authToken)).hasCause(failure);
        }
    }

    @Test
    void getUserInfo0RejectsBusinessError() throws Exception {
        String authToken = "authToken-value";
        UserInfoServiceImpl target = new UserInfoServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("alipay.user.info.share"), anyMap(), isNull())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.getUserInfo(authToken)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }
}
