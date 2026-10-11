package com.example.demo.dingtalk.manager;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.taobao.api.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.alibaba.fastjson2.JSON;

class UserInfoServiceImplTest {
    @Test
    void getUserInfo0BuildsRequest() throws Exception {
        String accessToken = "accessToken-value";
        String userid = "userid-value";
        UserInfoServiceImpl target = new UserInfoServiceImpl();
        OapiV2UserGetResponse response = JSON.parseObject("{\"errcode\": 0, \"errmsg\": \"ok\", \"mediaId\": \"media-result\", \"taskId\": 42, \"result\": {\"userid\": \"user-result\", \"name\": \"User\"}}", OapiV2UserGetResponse.class);
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) ->
                when(client.execute(any(OapiV2UserGetRequest.class), eq(accessToken))).thenReturn(response))) {
            var result = target.getUserInfo(accessToken, userid);
            assertThat(result.getUserid()).isEqualTo("user-result");
            ArgumentCaptor<OapiV2UserGetRequest> captor = ArgumentCaptor.forClass(OapiV2UserGetRequest.class);
            verify(clients.constructed().getFirst()).execute(captor.capture(), eq(accessToken));
            OapiV2UserGetRequest request = captor.getValue();
            assertThat(request.getUserid()).isEqualTo(userid);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "empty", "business", "transport" })
    void getUserInfo0RejectsFailedCalls(String kind) throws Exception {
        String accessToken = "accessToken-value";
        String userid = "userid-value";
        UserInfoServiceImpl target = new UserInfoServiceImpl();
        OapiV2UserGetResponse response = JSON.parseObject("{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", OapiV2UserGetResponse.class);
        ApiException failure = new ApiException("transport unavailable");
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) -> {
            if (kind.equals("transport")) {
                when(client.execute(any(OapiV2UserGetRequest.class), eq(accessToken))).thenThrow(failure);
            } else {
                when(client.execute(any(OapiV2UserGetRequest.class), eq(accessToken))).thenReturn(kind.equals("empty") ? null : response);
            }
        })) {
            if (kind.equals("transport")) { assertThatThrownBy(() -> target.getUserInfo(accessToken, userid)).hasCause(failure);
            }
            else { assertThatThrownBy(() -> target.getUserInfo(accessToken, userid)).hasMessage(kind.equals("empty") ? "response is null." : "provider rejected");
            }
        }
    }

    @Test
    void getUserid1BuildsRequest() throws Exception {
        String accessToken = "accessToken-value";
        String mobile = "mobile-value";
        UserInfoServiceImpl target = new UserInfoServiceImpl();
        OapiV2UserGetbymobileResponse response = JSON.parseObject("{\"errcode\": 0, \"errmsg\": \"ok\", \"mediaId\": \"media-result\", \"taskId\": 42, \"result\": {\"userid\": \"user-result\", \"name\": \"User\"}}", OapiV2UserGetbymobileResponse.class);
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) ->
                when(client.execute(any(OapiV2UserGetbymobileRequest.class), eq(accessToken))).thenReturn(response))) {
            var result = target.getUserid(accessToken, mobile);
            assertThat(result).isEqualTo("user-result");
            ArgumentCaptor<OapiV2UserGetbymobileRequest> captor = ArgumentCaptor.forClass(OapiV2UserGetbymobileRequest.class);
            verify(clients.constructed().getFirst()).execute(captor.capture(), eq(accessToken));
            OapiV2UserGetbymobileRequest request = captor.getValue();
            assertThat(request.getMobile()).isEqualTo(mobile);
            assertThat(request.getSupportExclusiveAccountSearch()).isEqualTo(Boolean.FALSE);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "empty", "business", "transport" })
    void getUserid1RejectsFailedCalls(String kind) throws Exception {
        String accessToken = "accessToken-value";
        String mobile = "mobile-value";
        UserInfoServiceImpl target = new UserInfoServiceImpl();
        OapiV2UserGetbymobileResponse response = JSON.parseObject("{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", OapiV2UserGetbymobileResponse.class);
        ApiException failure = new ApiException("transport unavailable");
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) -> {
            if (kind.equals("transport")) {
                when(client.execute(any(OapiV2UserGetbymobileRequest.class), eq(accessToken))).thenThrow(failure);
            } else {
                when(client.execute(any(OapiV2UserGetbymobileRequest.class), eq(accessToken))).thenReturn(kind.equals("empty") ? null : response);
            }
        })) {
            if (kind.equals("transport")) { assertThatThrownBy(() -> target.getUserid(accessToken, mobile)).hasCause(failure);
            }
            else { assertThatThrownBy(() -> target.getUserid(accessToken, mobile)).hasMessage(kind.equals("empty") ? "response is null." : "provider rejected");
            }
        }
    }
}
