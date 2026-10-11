package com.example.demo.dingtalk.manager;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.taobao.api.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.alibaba.fastjson2.JSON;

class MediaServiceImplTest {
    @Test
    void upload0BuildsRequest() throws Exception {
        String accessToken = "accessToken-value";
        String type = "type-value";
        String fileName = "fileName-value";
        byte[] content = new byte[] { 1, 2, 3 };
        MediaServiceImpl target = new MediaServiceImpl();
        OapiMediaUploadResponse response = JSON.parseObject("{\"errcode\": 0, \"errmsg\": \"ok\", \"mediaId\": \"media-result\", \"taskId\": 42, \"result\": {\"userid\": \"user-result\", \"name\": \"User\"}}", OapiMediaUploadResponse.class);
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) ->
                when(client.execute(any(OapiMediaUploadRequest.class), eq(accessToken))).thenReturn(response))) {
            var result = target.upload(accessToken, type, fileName, content);
            assertThat(result).isEqualTo("media-result");
            ArgumentCaptor<OapiMediaUploadRequest> captor = ArgumentCaptor.forClass(OapiMediaUploadRequest.class);
            verify(clients.constructed().getFirst()).execute(captor.capture(), eq(accessToken));
            OapiMediaUploadRequest request = captor.getValue();
            assertThat(request.getType()).isEqualTo(type);
            assertThat(request.getMedia().getFileName()).isEqualTo(fileName);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "empty", "business", "transport" })
    void upload0RejectsFailedCalls(String kind) throws Exception {
        String accessToken = "accessToken-value";
        String type = "type-value";
        String fileName = "fileName-value";
        byte[] content = new byte[] { 1, 2, 3 };
        MediaServiceImpl target = new MediaServiceImpl();
        OapiMediaUploadResponse response = JSON.parseObject("{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", OapiMediaUploadResponse.class);
        ApiException failure = new ApiException("transport unavailable");
        try (var clients = mockConstruction(DefaultDingTalkClient.class, (client, context) -> {
            if (kind.equals("transport")) {
                when(client.execute(any(OapiMediaUploadRequest.class), eq(accessToken))).thenThrow(failure);
            } else {
                when(client.execute(any(OapiMediaUploadRequest.class), eq(accessToken))).thenReturn(kind.equals("empty") ? null : response);
            }
        })) {
            if (kind.equals("transport")) { assertThatThrownBy(() -> target.upload(accessToken, type, fileName, content)).hasCause(failure);
            }
            else { assertThatThrownBy(() -> target.upload(accessToken, type, fileName, content)).hasMessage(kind.equals("empty") ? "response is null." : "provider rejected");
            }
        }
    }
}
