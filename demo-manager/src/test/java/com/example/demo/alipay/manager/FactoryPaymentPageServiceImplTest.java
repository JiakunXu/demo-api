package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FactoryPaymentPageServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void pay0BuildsRequestAndReturnsResult() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String subject = "subject-value";
        String outTradeNo = "outTradeNo-value";
        String totalAmount = "totalAmount-value";
        String returnUrl = "returnUrl-value";
        FactoryPaymentPageServiceImpl target = new FactoryPaymentPageServiceImpl();
        com.alipay.easysdk.payment.page.Client client = mock(com.alipay.easysdk.payment.page.Client.class, RETURNS_SELF);
        AlipayTradePagePayResponse response = new AlipayTradePagePayResponse();
        response.body = "<form>payment</form>";

        when(client.pay(subject, outTradeNo, totalAmount, returnUrl)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Page).thenReturn(client);
            assertThat(target.pay(appAuthToken, subject, outTradeNo, totalAmount, returnUrl)).isSameAs(response);
            verify(client).pay(subject, outTradeNo, totalAmount, returnUrl);
            verify(client).agent(appAuthToken);
        }
    }

    @Test
    void pay0PreservesSdkFailure() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String subject = "subject-value";
        String outTradeNo = "outTradeNo-value";
        String totalAmount = "totalAmount-value";
        String returnUrl = "returnUrl-value";
        FactoryPaymentPageServiceImpl target = new FactoryPaymentPageServiceImpl();
        com.alipay.easysdk.payment.page.Client client = mock(com.alipay.easysdk.payment.page.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.pay(subject, outTradeNo, totalAmount, returnUrl)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Page).thenReturn(client);
            assertThatThrownBy(() -> target.pay(appAuthToken, subject, outTradeNo, totalAmount, returnUrl)).hasCause(failure);
        }
    }
}
