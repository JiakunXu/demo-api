package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FactoryPaymentCommonServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void create0BuildsRequestAndReturnsResult() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String subject = "subject-value";
        String outTradeNo = "outTradeNo-value";
        String totalAmount = "totalAmount-value";
        String buyerId = "buyerId-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeCreateResponse response = new AlipayTradeCreateResponse();
        response.code = "10000";

        when(client.create(subject, outTradeNo, totalAmount, buyerId)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThat(target.create(appAuthToken, subject, outTradeNo, totalAmount, buyerId)).isSameAs(response);
            verify(client).create(subject, outTradeNo, totalAmount, buyerId);
            verify(client).agent(appAuthToken);
        }
    }

    @Test
    void create0PreservesSdkFailure() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String subject = "subject-value";
        String outTradeNo = "outTradeNo-value";
        String totalAmount = "totalAmount-value";
        String buyerId = "buyerId-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.create(subject, outTradeNo, totalAmount, buyerId)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.create(appAuthToken, subject, outTradeNo, totalAmount, buyerId)).hasCause(failure);
        }
    }

    @Test
    void create0RejectsBusinessError() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String subject = "subject-value";
        String outTradeNo = "outTradeNo-value";
        String totalAmount = "totalAmount-value";
        String buyerId = "buyerId-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeCreateResponse response = new AlipayTradeCreateResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.create(subject, outTradeNo, totalAmount, buyerId)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.create(appAuthToken, subject, outTradeNo, totalAmount, buyerId)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void query1BuildsRequestAndReturnsResult() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeQueryResponse response = new AlipayTradeQueryResponse();
        response.code = "10000";

        when(client.query(outTradeNo)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThat(target.query(appAuthToken, outTradeNo)).isSameAs(response);
            verify(client).query(outTradeNo);
            verify(client).agent(appAuthToken);
        }
    }

    @Test
    void query1PreservesSdkFailure() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.query(outTradeNo)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.query(appAuthToken, outTradeNo)).hasCause(failure);
        }
    }

    @Test
    void query1RejectsBusinessError() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeQueryResponse response = new AlipayTradeQueryResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.query(outTradeNo)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.query(appAuthToken, outTradeNo)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void refund2BuildsRequestAndReturnsResult() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        String refundAmount = "refundAmount-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeRefundResponse response = new AlipayTradeRefundResponse();
        response.code = "10000";

        when(client.refund(outTradeNo, refundAmount)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThat(target.refund(appAuthToken, outTradeNo, refundAmount)).isSameAs(response);
            verify(client).refund(outTradeNo, refundAmount);
            verify(client).agent(appAuthToken);
        }
    }

    @Test
    void refund2PreservesSdkFailure() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        String refundAmount = "refundAmount-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.refund(outTradeNo, refundAmount)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.refund(appAuthToken, outTradeNo, refundAmount)).hasCause(failure);
        }
    }

    @Test
    void refund2RejectsBusinessError() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        String refundAmount = "refundAmount-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeRefundResponse response = new AlipayTradeRefundResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.refund(outTradeNo, refundAmount)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.refund(appAuthToken, outTradeNo, refundAmount)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void close3BuildsRequestAndReturnsResult() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeCloseResponse response = new AlipayTradeCloseResponse();
        response.code = "10000";

        when(client.close(outTradeNo)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThat(target.close(appAuthToken, outTradeNo)).isSameAs(response);
            verify(client).close(outTradeNo);
            verify(client).agent(appAuthToken);
        }
    }

    @Test
    void close3PreservesSdkFailure() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.close(outTradeNo)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.close(appAuthToken, outTradeNo)).hasCause(failure);
        }
    }

    @Test
    void close3RejectsBusinessError() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeCloseResponse response = new AlipayTradeCloseResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.close(outTradeNo)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.close(appAuthToken, outTradeNo)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void cancel4BuildsRequestAndReturnsResult() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeCancelResponse response = new AlipayTradeCancelResponse();
        response.code = "10000";

        when(client.cancel(outTradeNo)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThat(target.cancel(appAuthToken, outTradeNo)).isSameAs(response);
            verify(client).cancel(outTradeNo);
            verify(client).agent(appAuthToken);
        }
    }

    @Test
    void cancel4PreservesSdkFailure() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.cancel(outTradeNo)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.cancel(appAuthToken, outTradeNo)).hasCause(failure);
        }
    }

    @Test
    void cancel4RejectsBusinessError() throws Exception {
        String appAuthToken = "appAuthToken-value";
        String outTradeNo = "outTradeNo-value";
        FactoryPaymentCommonServiceImpl target = new FactoryPaymentCommonServiceImpl();
        com.alipay.easysdk.payment.common.Client client = mock(com.alipay.easysdk.payment.common.Client.class, RETURNS_SELF);
        AlipayTradeCancelResponse response = new AlipayTradeCancelResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.cancel(outTradeNo)).thenReturn(response);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> target.cancel(appAuthToken, outTradeNo)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }
}
