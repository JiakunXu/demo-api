package com.example.demo.dingtalk.manager;

import com.aliyun.dingtalkoauth2_1_0.Client;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.tea.TeaException;
import com.example.demo.dingtalk.api.bo.AccessToken;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AccessTokenServiceImplTest {
    @Test
    void getAccessToken0BuildsRequestAndMapsResponse() throws Exception {
        String appKey = "appKey-value";
        String appSecret = "appSecret-value";
        AccessTokenServiceImpl target = new AccessTokenServiceImpl();
        GetAccessTokenResponse response = JSON.parseObject("{\"body\": {\"accessToken\": \"access-token\", \"expireIn\": 7200}}", GetAccessTokenResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.getAccessToken(any(GetAccessTokenRequest.class))).thenReturn(response))) {
            assertThat(target.getAccessToken(appKey, appSecret)).usingRecursiveComparison().isEqualTo(JSON.parseObject("{\"accessToken\":\"access-token\",\"expireIn\":7200}", AccessToken.class));
            ArgumentCaptor<GetAccessTokenRequest> captor = ArgumentCaptor.forClass(GetAccessTokenRequest.class);

            verify(clients.constructed().getFirst()).getAccessToken(captor.capture());
            GetAccessTokenRequest request = captor.getValue();
            assertThat(request.getAppKey()).isEqualTo(appKey); assertThat(request.getAppSecret()).isEqualTo(appSecret);
        }
    }

    @Test
    void getAccessToken0PreservesTransportFailure() throws Exception {
        String appKey = "appKey-value";
        String appSecret = "appSecret-value";
        AccessTokenServiceImpl target = new AccessTokenServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.getAccessToken(any(GetAccessTokenRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.getAccessToken(appKey, appSecret)).hasCause(failure);
        }
    }

    @Test
    void getAccessToken0UsesProviderErrorMessage() throws Exception {
        String appKey = "appKey-value";
        String appSecret = "appSecret-value";
        AccessTokenServiceImpl target = new AccessTokenServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.getAccessToken(any(GetAccessTokenRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.getAccessToken(appKey, appSecret)).hasMessage("provider rejected");
        }
    }
}
