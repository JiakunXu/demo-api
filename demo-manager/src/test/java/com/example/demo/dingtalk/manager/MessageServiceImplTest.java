package com.example.demo.dingtalk.manager;

import com.alibaba.fastjson2.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationRecallRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationRecallResponse;
import com.taobao.api.ApiException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MessageServiceImplTest {
    @Test
    void send0BuildsRequest() throws Exception {
        String accessToken = "accessToken-value";
        Long agentId = 42L;
        String useridList = "useridList-value";
        String mediaId = "mediaId-value";
        MessageServiceImpl target = new MessageServiceImpl();
        OapiMessageCorpconversationAsyncsendV2Response response = JSON.parseObject("{\"errcode\": 0, \"errmsg\": \"ok\", \"mediaId\": \"media-result\", \"taskId\": 42, \"result\": {\"userid\": \"user-result\", \"name\": \"User\"}}", OapiMessageCorpconversationAsyncsendV2Response.class);
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) ->
                when(client.execute(any(OapiMessageCorpconversationAsyncsendV2Request.class), eq(accessToken))).thenReturn(response))) {
            var result = target.send(accessToken, agentId, useridList, mediaId);
            assertThat(result).isEqualTo(42L);
            ArgumentCaptor<OapiMessageCorpconversationAsyncsendV2Request> captor = ArgumentCaptor.forClass(OapiMessageCorpconversationAsyncsendV2Request.class);
            verify(clients.constructed().getFirst()).execute(captor.capture(), eq(accessToken));
            OapiMessageCorpconversationAsyncsendV2Request request = captor.getValue();
            assertThat(request.getAgentId()).isEqualTo(agentId);
            assertThat(request.getUseridList()).isEqualTo(useridList);
            assertThat(JSON.parseObject(request.getMsg()).getString("msgtype")).isEqualTo("file");
            assertThat(JSON.parseObject(request.getMsg()).getJSONObject("file").getString("media_id")).isEqualTo(mediaId);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "empty", "business", "transport" })
    void send0RejectsFailedCalls(String kind) throws Exception {
        String accessToken = "accessToken-value";
        Long agentId = 42L;
        String useridList = "useridList-value";
        String mediaId = "mediaId-value";
        MessageServiceImpl target = new MessageServiceImpl();
        OapiMessageCorpconversationAsyncsendV2Response response = JSON.parseObject("{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", OapiMessageCorpconversationAsyncsendV2Response.class);
        ApiException failure = new ApiException("transport unavailable");
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) -> {
            if (kind.equals("transport")) {
                when(client.execute(any(OapiMessageCorpconversationAsyncsendV2Request.class), eq(accessToken))).thenThrow(failure);
            } else {
                when(client.execute(any(OapiMessageCorpconversationAsyncsendV2Request.class), eq(accessToken))).thenReturn(kind.equals("empty") ? null : response);
            }
        })) {
            if (kind.equals("transport")) { assertThatThrownBy(() -> target.send(accessToken, agentId, useridList, mediaId)).hasCause(failure);
            }
            else { assertThatThrownBy(() -> target.send(accessToken, agentId, useridList, mediaId)).hasMessage(kind.equals("empty") ? "response is null." : "provider rejected");
            }
        }
    }

    @Test
    void send1BuildsRequest() throws Exception {
        String accessToken = "accessToken-value";
        Long agentId = 42L;
        String useridList = "useridList-value";
        String title = "title-value";
        String text = "text-value";
        MessageServiceImpl target = new MessageServiceImpl();
        OapiMessageCorpconversationAsyncsendV2Response response = JSON.parseObject("{\"errcode\": 0, \"errmsg\": \"ok\", \"mediaId\": \"media-result\", \"taskId\": 42, \"result\": {\"userid\": \"user-result\", \"name\": \"User\"}}", OapiMessageCorpconversationAsyncsendV2Response.class);
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) ->
                when(client.execute(any(OapiMessageCorpconversationAsyncsendV2Request.class), eq(accessToken))).thenReturn(response))) {
            var result = target.send(accessToken, agentId, useridList, title, text);
            assertThat(result).isEqualTo(42L);
            ArgumentCaptor<OapiMessageCorpconversationAsyncsendV2Request> captor = ArgumentCaptor.forClass(OapiMessageCorpconversationAsyncsendV2Request.class);
            verify(clients.constructed().getFirst()).execute(captor.capture(), eq(accessToken));
            OapiMessageCorpconversationAsyncsendV2Request request = captor.getValue();
            assertThat(request.getAgentId()).isEqualTo(agentId);
            assertThat(request.getUseridList()).isEqualTo(useridList);
            assertThat(JSON.parseObject(request.getMsg()).getString("msgtype")).isEqualTo("markdown");
            assertThat(JSON.parseObject(request.getMsg()).getJSONObject("markdown").getString("title")).isEqualTo(title);
            assertThat(JSON.parseObject(request.getMsg()).getJSONObject("markdown").getString("text")).isEqualTo(text);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "empty", "business", "transport" })
    void send1RejectsFailedCalls(String kind) throws Exception {
        String accessToken = "accessToken-value";
        Long agentId = 42L;
        String useridList = "useridList-value";
        String title = "title-value";
        String text = "text-value";
        MessageServiceImpl target = new MessageServiceImpl();
        OapiMessageCorpconversationAsyncsendV2Response response = JSON.parseObject("{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", OapiMessageCorpconversationAsyncsendV2Response.class);
        ApiException failure = new ApiException("transport unavailable");
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) -> {
            if (kind.equals("transport")) {
                when(client.execute(any(OapiMessageCorpconversationAsyncsendV2Request.class), eq(accessToken))).thenThrow(failure);
            } else {
                when(client.execute(any(OapiMessageCorpconversationAsyncsendV2Request.class), eq(accessToken))).thenReturn(kind.equals("empty") ? null : response);
            }
        })) {
            if (kind.equals("transport")) { assertThatThrownBy(() -> target.send(accessToken, agentId, useridList, title, text)).hasCause(failure);
            }
            else { assertThatThrownBy(() -> target.send(accessToken, agentId, useridList, title, text)).hasMessage(kind.equals("empty") ? "response is null." : "provider rejected");
            }
        }
    }

    @Test
    void recall2BuildsRequest() throws Exception {
        String accessToken = "accessToken-value";
        Long agentId = 42L;
        Long msgTaskId = 42L;
        MessageServiceImpl target = new MessageServiceImpl();
        OapiMessageCorpconversationRecallResponse response = JSON.parseObject("{\"errcode\": 0, \"errmsg\": \"ok\", \"mediaId\": \"media-result\", \"taskId\": 42, \"result\": {\"userid\": \"user-result\", \"name\": \"User\"}}", OapiMessageCorpconversationRecallResponse.class);
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) ->
                when(client.execute(any(OapiMessageCorpconversationRecallRequest.class), eq(accessToken))).thenReturn(response))) {
            target.recall(accessToken, agentId, msgTaskId);

            ArgumentCaptor<OapiMessageCorpconversationRecallRequest> captor = ArgumentCaptor.forClass(OapiMessageCorpconversationRecallRequest.class);
            verify(clients.constructed().getFirst()).execute(captor.capture(), eq(accessToken));
            OapiMessageCorpconversationRecallRequest request = captor.getValue();
            assertThat(request.getAgentId()).isEqualTo(agentId);
            assertThat(request.getMsgTaskId()).isEqualTo(msgTaskId);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "empty", "business", "transport" })
    void recall2RejectsFailedCalls(String kind) throws Exception {
        String accessToken = "accessToken-value";
        Long agentId = 42L;
        Long msgTaskId = 42L;
        MessageServiceImpl target = new MessageServiceImpl();
        OapiMessageCorpconversationRecallResponse response = JSON.parseObject("{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", OapiMessageCorpconversationRecallResponse.class);
        ApiException failure = new ApiException("transport unavailable");
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) -> {
            if (kind.equals("transport")) {
                when(client.execute(any(OapiMessageCorpconversationRecallRequest.class), eq(accessToken))).thenThrow(failure);
            } else {
                when(client.execute(any(OapiMessageCorpconversationRecallRequest.class), eq(accessToken))).thenReturn(kind.equals("empty") ? null : response);
            }
        })) {
            if (kind.equals("transport")) { assertThatThrownBy(() -> target.recall(accessToken, agentId, msgTaskId)).hasCause(failure);
            }
            else { assertThatThrownBy(() -> target.recall(accessToken, agentId, msgTaskId)).hasMessage(kind.equals("empty") ? "response is null." : "provider rejected");
            }
        }
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.ValueSource(strings = {"agree", "refuse", "terminate"})
    void callbackDecryptsEventAndReturnsEncryptedAcknowledgment(String result) throws Exception {
        var service = new MessageServiceImpl();
        org.springframework.test.util.ReflectionTestUtils.setField(service, "token", "token");
        org.springframework.test.util.ReflectionTestUtils.setField(service, "encodingAesKey", "key");
        org.springframework.test.util.ReflectionTestUtils.setField(service, "appKey", "app");
        var acknowledgment = java.util.Map.of("encrypt", "encrypted-success");
        try (MockedConstruction<DingCallbackCrypto> callbacks = mockConstruction(DingCallbackCrypto.class, (callback, context) -> {
            assertThat(context.arguments().toArray()).containsExactly("token", "key", "app");
            when(callback.getDecryptMsg("signature", "123", "nonce", "cipher")).thenReturn("{\"EventType\":\"bpms_instance_change\",\"type\":\"" + (result.equals("terminate") ? "terminate" : "finish") + "\",\"result\":\"" + result + "\"}");
            when(callback.getEncryptedMap("success")).thenReturn(acknowledgment);
        })) {
            assertThat(service.notify("signature", "123", "nonce", "cipher")).isEqualTo(acknowledgment);
            verify(callbacks.constructed().getFirst()).getDecryptMsg("signature", "123", "nonce", "cipher");
            verify(callbacks.constructed().getFirst()).getEncryptedMap("success");
        }
    }

    @Test
    void callbackRejectsInvalidCryptography() throws Exception {
        var failure = mock(DingCallbackCrypto.DingTalkEncryptException.class);
        try (MockedConstruction<DingCallbackCrypto> callbacks = mockConstruction(DingCallbackCrypto.class, (callback, context) -> when(callback.getDecryptMsg(anyString(), anyString(), anyString(), anyString())).thenThrow(failure))) {
            assertThat(new MessageServiceImpl().notify("signature", "123", "nonce", "cipher")).isNull();
            verify(callbacks.constructed().getFirst(), never()).getEncryptedMap(anyString());
        }
    }
}
