package com.example.demo.aliyun.manager;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SmsServiceImplTest {
    @Test
    void send0BuildsRequestAndMapsResponse() throws Exception {
        String signName = "signName-value";
        String templateCode = "templateCode-value";
        String templateParam = "templateParam-value";
        String phoneNumbers = "phoneNumbers-value";
        SmsServiceImpl target = new SmsServiceImpl();
        SendSmsResponse response = JSON.parseObject("{\"body\": {\"code\": \"OK\", \"message\": \"accepted\", \"bizId\": \"biz-1\", \"callId\": \"call-1\", \"envId\": \"env-1\"}}", SendSmsResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.sendSms(any(SendSmsRequest.class))).thenReturn(response))) {
            assertThat(target.send(signName, templateCode, templateParam, phoneNumbers)).isEqualTo("biz-1");
            ArgumentCaptor<SendSmsRequest> captor = ArgumentCaptor.forClass(SendSmsRequest.class);

            verify(clients.constructed().getFirst()).sendSms(captor.capture());
            SendSmsRequest request = captor.getValue();
            assertThat(request.getSignName()).isEqualTo(signName);
            assertThat(request.getTemplateCode()).isEqualTo(templateCode);
            assertThat(request.getTemplateParam()).isEqualTo(templateParam);
            assertThat(request.getPhoneNumbers()).isEqualTo(phoneNumbers);
        }
    }

    @Test
    void send0PreservesTransportFailure() throws Exception {
        String signName = "signName-value";
        String templateCode = "templateCode-value";
        String templateParam = "templateParam-value";
        String phoneNumbers = "phoneNumbers-value";
        SmsServiceImpl target = new SmsServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.sendSms(any(SendSmsRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.send(signName, templateCode, templateParam, phoneNumbers)).hasCause(failure);
        }
    }

    @Test
    void send0UsesProviderErrorMessage() throws Exception {
        String signName = "signName-value";
        String templateCode = "templateCode-value";
        String templateParam = "templateParam-value";
        String phoneNumbers = "phoneNumbers-value";
        SmsServiceImpl target = new SmsServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.sendSms(any(SendSmsRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.send(signName, templateCode, templateParam, phoneNumbers)).hasMessage("provider rejected");
        }
    }

    @Test
    void send0RejectsBusinessError() throws Exception {
        String signName = "signName-value";
        String templateCode = "templateCode-value";
        String templateParam = "templateParam-value";
        String phoneNumbers = "phoneNumbers-value";
        SmsServiceImpl target = new SmsServiceImpl();
        SendSmsResponse response = JSON.parseObject("{\"body\": {\"code\": \"DENIED\", \"message\": \"provider rejected\"}}", SendSmsResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.sendSms(any(SendSmsRequest.class))).thenReturn(response))) {
            assertThatThrownBy(() -> target.send(signName, templateCode, templateParam, phoneNumbers)).hasMessage("provider rejected");
        }
    }
}
