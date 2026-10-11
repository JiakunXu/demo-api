package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FactoryPaymentWapServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void pay0BuildsRequestAndReturnsResult() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String subject = "subject-value";
        String outTradeNo = "outTradeNo-value";
        String totalAmount = "totalAmount-value";
        String quitUrl = "quitUrl-value";
        String returnUrl = "returnUrl-value";
        FactoryPaymentWapServiceImpl target = new FactoryPaymentWapServiceImpl();
        com.alipay.easysdk.payment.wap.Client client = mock(com.alipay.easysdk.payment.wap.Client.class, RETURNS_SELF);
        AlipayTradeWapPayResponse response = new AlipayTradeWapPayResponse();
        response.body = "<form>payment</form>";

        when(client.pay(subject, outTradeNo, totalAmount, quitUrl, returnUrl)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Wap).thenReturn(client);
            assertThat(target.pay(appAuthToken, subject, outTradeNo, totalAmount, quitUrl, returnUrl)).isSameAs(response);
            verify(client).pay(subject, outTradeNo, totalAmount, quitUrl, returnUrl);
            verify(client).agent(appAuthToken);
        }
    }

    @Test
    void pay0PreservesSdkFailure() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String subject = "subject-value";
        String outTradeNo = "outTradeNo-value";
        String totalAmount = "totalAmount-value";
        String quitUrl = "quitUrl-value";
        String returnUrl = "returnUrl-value";
        FactoryPaymentWapServiceImpl target = new FactoryPaymentWapServiceImpl();
        com.alipay.easysdk.payment.wap.Client client = mock(com.alipay.easysdk.payment.wap.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.pay(subject, outTradeNo, totalAmount, quitUrl, returnUrl)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Wap).thenReturn(client);
            assertThatThrownBy(() -> target.pay(appAuthToken, subject, outTradeNo, totalAmount, quitUrl, returnUrl)).hasCause(failure);
        }
    }
}
