package com.example.demo.aliyun.manager;

import com.aliyun.dyvmsapi20170525.Client;
import com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest;
import com.aliyun.dyvmsapi20170525.models.SingleCallByTtsResponse;
import com.aliyun.dyvmsapi20170525.models.SingleCallByVoiceRequest;
import com.aliyun.dyvmsapi20170525.models.SingleCallByVoiceResponse;
import com.aliyun.tea.TeaException;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VmsServiceImplTest {
    @Test
    void singleCallByVoice0BuildsRequestAndMapsResponse() throws Exception {
        String calledNumber = "calledNumber-value";
        String voiceCode = "voiceCode-value";
        VmsServiceImpl target = new VmsServiceImpl();
        SingleCallByVoiceResponse response = JSON.parseObject("{\"body\": {\"code\": \"OK\", \"message\": \"accepted\", \"bizId\": \"biz-1\", \"callId\": \"call-1\", \"envId\": \"env-1\"}}", SingleCallByVoiceResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleCallByVoice(any(SingleCallByVoiceRequest.class))).thenReturn(response))) {
            assertThat(target.singleCallByVoice(calledNumber, voiceCode)).isEqualTo("call-1");
            ArgumentCaptor<SingleCallByVoiceRequest> captor = ArgumentCaptor.forClass(SingleCallByVoiceRequest.class);

            verify(clients.constructed().getFirst()).singleCallByVoice(captor.capture());
            SingleCallByVoiceRequest request = captor.getValue();
            assertThat(request.getCalledNumber()).isEqualTo(calledNumber);
            assertThat(request.getVoiceCode()).isEqualTo(voiceCode);
        }
    }

    @Test
    void singleCallByVoice0PreservesTransportFailure() throws Exception {
        String calledNumber = "calledNumber-value";
        String voiceCode = "voiceCode-value";
        VmsServiceImpl target = new VmsServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleCallByVoice(any(SingleCallByVoiceRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.singleCallByVoice(calledNumber, voiceCode)).hasCause(failure);
        }
    }

    @Test
    void singleCallByVoice0UsesProviderErrorMessage() throws Exception {
        String calledNumber = "calledNumber-value";
        String voiceCode = "voiceCode-value";
        VmsServiceImpl target = new VmsServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleCallByVoice(any(SingleCallByVoiceRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.singleCallByVoice(calledNumber, voiceCode)).hasMessage("provider rejected");
        }
    }

    @Test
    void singleCallByVoice0RejectsBusinessError() throws Exception {
        String calledNumber = "calledNumber-value";
        String voiceCode = "voiceCode-value";
        VmsServiceImpl target = new VmsServiceImpl();
        SingleCallByVoiceResponse response = JSON.parseObject("{\"body\": {\"code\": \"DENIED\", \"message\": \"provider rejected\"}}", SingleCallByVoiceResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleCallByVoice(any(SingleCallByVoiceRequest.class))).thenReturn(response))) {
            assertThatThrownBy(() -> target.singleCallByVoice(calledNumber, voiceCode)).hasMessage("provider rejected");
        }
    }

    @Test
    void singleCallByTts1BuildsRequestAndMapsResponse() throws Exception {
        String calledNumber = "calledNumber-value";
        String ttsCode = "ttsCode-value";
        String ttsParam = "ttsParam-value";
        VmsServiceImpl target = new VmsServiceImpl();
        SingleCallByTtsResponse response = JSON.parseObject("{\"body\": {\"code\": \"OK\", \"message\": \"accepted\", \"bizId\": \"biz-1\", \"callId\": \"call-1\", \"envId\": \"env-1\"}}", SingleCallByTtsResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleCallByTts(any(SingleCallByTtsRequest.class))).thenReturn(response))) {
            assertThat(target.singleCallByTts(calledNumber, ttsCode, ttsParam)).isEqualTo("call-1");
            ArgumentCaptor<SingleCallByTtsRequest> captor = ArgumentCaptor.forClass(SingleCallByTtsRequest.class);

            verify(clients.constructed().getFirst()).singleCallByTts(captor.capture());
            SingleCallByTtsRequest request = captor.getValue();
            assertThat(request.getCalledNumber()).isEqualTo(calledNumber);
            assertThat(request.getTtsCode()).isEqualTo(ttsCode);
            assertThat(request.getTtsParam()).isEqualTo(ttsParam);
        }
    }

    @Test
    void singleCallByTts1PreservesTransportFailure() throws Exception {
        String calledNumber = "calledNumber-value";
        String ttsCode = "ttsCode-value";
        String ttsParam = "ttsParam-value";
        VmsServiceImpl target = new VmsServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleCallByTts(any(SingleCallByTtsRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.singleCallByTts(calledNumber, ttsCode, ttsParam)).hasCause(failure);
        }
    }

    @Test
    void singleCallByTts1UsesProviderErrorMessage() throws Exception {
        String calledNumber = "calledNumber-value";
        String ttsCode = "ttsCode-value";
        String ttsParam = "ttsParam-value";
        VmsServiceImpl target = new VmsServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleCallByTts(any(SingleCallByTtsRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.singleCallByTts(calledNumber, ttsCode, ttsParam)).hasMessage("provider rejected");
        }
    }

    @Test
    void singleCallByTts1RejectsBusinessError() throws Exception {
        String calledNumber = "calledNumber-value";
        String ttsCode = "ttsCode-value";
        String ttsParam = "ttsParam-value";
        VmsServiceImpl target = new VmsServiceImpl();
        SingleCallByTtsResponse response = JSON.parseObject("{\"body\": {\"code\": \"DENIED\", \"message\": \"provider rejected\"}}", SingleCallByTtsResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleCallByTts(any(SingleCallByTtsRequest.class))).thenReturn(response))) {
            assertThatThrownBy(() -> target.singleCallByTts(calledNumber, ttsCode, ttsParam)).hasMessage("provider rejected");
        }
    }
}
