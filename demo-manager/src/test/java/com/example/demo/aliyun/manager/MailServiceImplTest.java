package com.example.demo.aliyun.manager;

import com.aliyun.dm20151123.Client;
import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.dm20151123.models.SingleSendMailResponse;
import com.aliyun.tea.TeaException;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MailServiceImplTest {
    @Test
    void singleSendMail0BuildsRequestAndMapsResponse() throws Exception {
        String accountName = "accountName-value";
        String toAddress = "toAddress-value";
        String subject = "subject-value";
        String textBody = "textBody-value";
        MailServiceImpl target = new MailServiceImpl();
        SingleSendMailResponse response = JSON.parseObject("{\"body\": {\"code\": \"OK\", \"message\": \"accepted\", \"bizId\": \"biz-1\", \"callId\": \"call-1\", \"envId\": \"env-1\"}}", SingleSendMailResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleSendMail(any(SingleSendMailRequest.class))).thenReturn(response))) {
            assertThat(target.singleSendMail(accountName, toAddress, subject, textBody)).isEqualTo("env-1");
            ArgumentCaptor<SingleSendMailRequest> captor = ArgumentCaptor.forClass(SingleSendMailRequest.class);

            verify(clients.constructed().getFirst()).singleSendMail(captor.capture());
            SingleSendMailRequest request = captor.getValue();
            assertThat(request.getAccountName()).isEqualTo(accountName);
            assertThat(request.getReplyToAddress()).isEqualTo(false);
            assertThat(request.getToAddress()).isEqualTo(toAddress);
            assertThat(request.getSubject()).isEqualTo(subject);
            assertThat(request.getTextBody()).isEqualTo(textBody);
            assertThat(request.getAddressType()).isZero();
            assertThat(request.getReplyToAddress()).isFalse();
        }
    }

    @Test
    void singleSendMail0PreservesTransportFailure() throws Exception {
        String accountName = "accountName-value";
        String toAddress = "toAddress-value";
        String subject = "subject-value";
        String textBody = "textBody-value";
        MailServiceImpl target = new MailServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleSendMail(any(SingleSendMailRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.singleSendMail(accountName, toAddress, subject, textBody)).hasCause(failure);
        }
    }

    @Test
    void singleSendMail0UsesProviderErrorMessage() throws Exception {
        String accountName = "accountName-value";
        String toAddress = "toAddress-value";
        String subject = "subject-value";
        String textBody = "textBody-value";
        MailServiceImpl target = new MailServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.singleSendMail(any(SingleSendMailRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.singleSendMail(accountName, toAddress, subject, textBody)).hasMessage("provider rejected");
        }
    }
}
