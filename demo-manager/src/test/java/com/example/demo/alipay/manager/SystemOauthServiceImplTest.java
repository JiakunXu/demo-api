package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alipay.easysdk.base.oauth.models.AlipaySystemOauthTokenResponse;
import com.alipay.easysdk.factory.Factory;
import com.example.demo.alipay.api.bo.token.OauthToken;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SystemOauthServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void getToken0BuildsRequestAndReturnsResult() throws Exception {
        String code = "code-value";
        SystemOauthServiceImpl target = new SystemOauthServiceImpl();
        com.alipay.easysdk.base.oauth.Client client = mock(com.alipay.easysdk.base.oauth.Client.class, RETURNS_SELF);
        AlipaySystemOauthTokenResponse response = new AlipaySystemOauthTokenResponse();
        response.code = "10000";
        response.httpBody = "{\"alipay_system_oauth_token_response\": {\"code\": \"10000\", \"user_id\": \"user-1\", \"access_token\": \"token-1\", \"order_id\": \"order-1\", \"out_biz_no\": \"business-1\"}}";

        when(client.getToken(code)).thenReturn(response);
        try (var factory = mockStatic(Factory.Base.class)) {
            factory.when(Factory.Base::OAuth).thenReturn(client);
            assertThat(target.getToken(code)).usingRecursiveComparison().isEqualTo(JSON.parseObject("{\"code\":\"10000\",\"user_id\":\"user-1\",\"access_token\":\"token-1\",\"order_id\":\"order-1\",\"out_biz_no\":\"business-1\"}", OauthToken.class));
            verify(client).getToken(code);

        }
    }

    @Test
    void getToken0PreservesSdkFailure() throws Exception {
        String code = "code-value";
        SystemOauthServiceImpl target = new SystemOauthServiceImpl();
        com.alipay.easysdk.base.oauth.Client client = mock(com.alipay.easysdk.base.oauth.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.getToken(code)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Base.class)) {
            factory.when(Factory.Base::OAuth).thenReturn(client);
            assertThatThrownBy(() -> target.getToken(code)).hasCause(failure);
        }
    }

    @Test
    void getToken0RejectsBusinessError() throws Exception {
        String code = "code-value";
        SystemOauthServiceImpl target = new SystemOauthServiceImpl();
        com.alipay.easysdk.base.oauth.Client client = mock(com.alipay.easysdk.base.oauth.Client.class, RETURNS_SELF);
        AlipaySystemOauthTokenResponse response = new AlipaySystemOauthTokenResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.getToken(code)).thenReturn(response);
        try (var factory = mockStatic(Factory.Base.class)) {
            factory.when(Factory.Base::OAuth).thenReturn(client);
            assertThatThrownBy(() -> target.getToken(code)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }
}
