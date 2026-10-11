package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.Client;
import com.alipay.easysdk.marketing.templatemessage.models.AlipayOpenAppMiniTemplatemessageSendResponse;
import org.mockito.MockedStatic;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceImplTest {
    @Test
    void verifiesAndMapsNotification() throws Exception {
        Client client = mock(Client.class);
        Map<String, String> parameters = Map.of("notify_id", "notify", "app_id", "app", "biz_content", "content", "charset", "utf-8", "msg_method", "method", "sign", "signature", "sign_type", "RSA2", "utc_timestamp", "123", "version", "1.0");
        when(client.verifyNotify(parameters)).thenReturn(true);
        try (MockedStatic<Factory.Payment> factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            var result = new MessageServiceImpl().notify(parameters);
            assertThat(result.getNotifyId()).isEqualTo("notify");
            assertThat(result.getAppId()).isEqualTo("app");
            assertThat(result.getBizContent()).isEqualTo("content");
            assertThat(result.getSign()).isEqualTo("signature");
            verify(client).verifyNotify(parameters);
        }
    }

    @Test
    void rejectsUnsignedNotification() throws Exception {
        Client client = mock(Client.class);
        try (MockedStatic<Factory.Payment> factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> new MessageServiceImpl().notify(Map.of())).hasMessageContaining("验签失败");
        }
    }

    @Test
    void sendsTemplateAndParsesProviderBody() throws Exception {
        var client = mock(com.alipay.easysdk.marketing.templatemessage.Client.class);
        var response = mock(AlipayOpenAppMiniTemplatemessageSendResponse.class);
        when(response.getHttpBody()).thenReturn("{\"alipay_open_app_mini_templatemessage_send_response\":{\"code\":\"10000\",\"msg\":\"Success\"}}");
        when(client.send("user", "form", "template", "page", "data")).thenReturn(response);
        try (MockedStatic<Factory.Marketing> factory = mockStatic(Factory.Marketing.class)) {
            factory.when(Factory.Marketing::TemplateMessage).thenReturn(client);
            assertThat(new MessageServiceImpl().send("user", "form", "template", "page", "data").getCode()).isEqualTo("10000");
            verify(client).send("user", "form", "template", "page", "data");
        }
    }

    @Test
    void preservesSendFailure() throws Exception {
        var client = mock(com.alipay.easysdk.marketing.templatemessage.Client.class);
        Exception failure = new Exception("network");
        when(client.send(anyString(), anyString(), anyString(), anyString(), anyString())).thenThrow(failure);
        try (MockedStatic<Factory.Marketing> factory = mockStatic(Factory.Marketing.class)) {
            factory.when(Factory.Marketing::TemplateMessage).thenReturn(client);
            assertThatThrownBy(() -> new MessageServiceImpl().send("user", "form", "template", "page", "data")).hasCause(failure);
        }
    }
}
