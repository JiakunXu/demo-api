package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericSDKResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ZhimaCreditPayAfterUseCreditBizOrderServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void order0BuildsRequestAndReturnsResult() throws Exception {
        String outOrderNo = "outOrderNo-value";
        String creditAgreementId = "creditAgreementId-value";
        String categoryId = "categoryId-value";
        BigDecimal orderAmount = new BigDecimal("12.34");
        String subject = "subject-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("out_order_no", outOrderNo);
        expectedBiz.put("credit_agreement_id", creditAgreementId);
        expectedBiz.put("category_id", categoryId);
        expectedBiz.put("order_amount", orderAmount);
        expectedBiz.put("amount_type", "ORDER_AMOUNT");
        expectedBiz.put("product_code", "CREDIT_PAY_AFTER_USE");
        expectedBiz.put("subject", subject);

        when(client.execute(eq("zhima.credit.payafteruse.creditbizorder.order"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.order(outOrderNo, creditAgreementId, categoryId, orderAmount, subject)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("zhima.credit.payafteruse.creditbizorder.order"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);

        }
    }

    @Test
    void order0PreservesSdkFailure() throws Exception {
        String outOrderNo = "outOrderNo-value";
        String creditAgreementId = "creditAgreementId-value";
        String categoryId = "categoryId-value";
        BigDecimal orderAmount = new BigDecimal("12.34");
        String subject = "subject-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("zhima.credit.payafteruse.creditbizorder.order"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.order(outOrderNo, creditAgreementId, categoryId, orderAmount, subject)).hasCause(failure);
        }
    }

    @Test
    void order0RejectsBusinessError() throws Exception {
        String outOrderNo = "outOrderNo-value";
        String creditAgreementId = "creditAgreementId-value";
        String categoryId = "categoryId-value";
        BigDecimal orderAmount = new BigDecimal("12.34");
        String subject = "subject-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("zhima.credit.payafteruse.creditbizorder.order"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.order(outOrderNo, creditAgreementId, categoryId, orderAmount, subject)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void create1BuildsRequestAndReturnsResult() throws Exception {
        String outOrderNo = "outOrderNo-value";
        BigDecimal orderAmount = new BigDecimal("12.34");
        String zmServiceId = "zmServiceId-value";
        String categoryId = "categoryId-value";
        String subject = "subject-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericSDKResponse response = new AlipayOpenApiGenericSDKResponse();
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("out_order_no", outOrderNo);
        expectedBiz.put("order_amount", orderAmount);
        expectedBiz.put("amount_type", "ORDER_AMOUNT");
        expectedBiz.put("zm_service_id", zmServiceId);
        expectedBiz.put("category_id", categoryId);
        expectedBiz.put("product_code", "CREDIT_PAY_AFTER_USE");
        expectedBiz.put("subject", subject);

        when(client.sdkExecute(eq("zhima.credit.payafteruse.creditbizorder.create"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.create(outOrderNo, orderAmount, zmServiceId, categoryId, subject)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).sdkExecute(eq("zhima.credit.payafteruse.creditbizorder.create"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);

        }
    }

    @Test
    void create1PreservesSdkFailure() throws Exception {
        String outOrderNo = "outOrderNo-value";
        BigDecimal orderAmount = new BigDecimal("12.34");
        String zmServiceId = "zmServiceId-value";
        String categoryId = "categoryId-value";
        String subject = "subject-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.sdkExecute(eq("zhima.credit.payafteruse.creditbizorder.create"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.create(outOrderNo, orderAmount, zmServiceId, categoryId, subject)).hasCause(failure);
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void query2BuildsRequestAndReturnsResult() throws Exception {
        String creditBizOrderId = "creditBizOrderId-value";
        String outOrderNo = "outOrderNo-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("credit_biz_order_id", creditBizOrderId);
        expectedBiz.put("out_order_no", outOrderNo);

        when(client.execute(eq("zhima.credit.payafteruse.creditbizorder.query"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.query(creditBizOrderId, outOrderNo)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("zhima.credit.payafteruse.creditbizorder.query"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);

        }
    }

    @Test
    void query2PreservesSdkFailure() throws Exception {
        String creditBizOrderId = "creditBizOrderId-value";
        String outOrderNo = "outOrderNo-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("zhima.credit.payafteruse.creditbizorder.query"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.query(creditBizOrderId, outOrderNo)).hasCause(failure);
        }
    }

    @Test
    void query2RejectsBusinessError() throws Exception {
        String creditBizOrderId = "creditBizOrderId-value";
        String outOrderNo = "outOrderNo-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("zhima.credit.payafteruse.creditbizorder.query"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.query(creditBizOrderId, outOrderNo)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void finish3BuildsRequestAndReturnsResult() throws Exception {
        String outRequestNo = "outRequestNo-value";
        String creditBizOrderId = "creditBizOrderId-value";
        String isFulfilled = "isFulfilled-value";
        String remark = "remark-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("out_request_no", outRequestNo);
        expectedBiz.put("credit_biz_order_id", creditBizOrderId);
        expectedBiz.put("is_fulfilled", isFulfilled);
        expectedBiz.put("remark", remark);

        when(client.execute(eq("zhima.credit.payafteruse.creditbizorder.finish"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.finish(outRequestNo, creditBizOrderId, isFulfilled, remark)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("zhima.credit.payafteruse.creditbizorder.finish"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);

        }
    }

    @Test
    void finish3PreservesSdkFailure() throws Exception {
        String outRequestNo = "outRequestNo-value";
        String creditBizOrderId = "creditBizOrderId-value";
        String isFulfilled = "isFulfilled-value";
        String remark = "remark-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("zhima.credit.payafteruse.creditbizorder.finish"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.finish(outRequestNo, creditBizOrderId, isFulfilled, remark)).hasCause(failure);
        }
    }

    @Test
    void finish3RejectsBusinessError() throws Exception {
        String outRequestNo = "outRequestNo-value";
        String creditBizOrderId = "creditBizOrderId-value";
        String isFulfilled = "isFulfilled-value";
        String remark = "remark-value";
        ZhimaCreditPayAfterUseCreditBizOrderServiceImpl target = new ZhimaCreditPayAfterUseCreditBizOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("zhima.credit.payafteruse.creditbizorder.finish"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.finish(outRequestNo, creditBizOrderId, isFulfilled, remark)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }
}
