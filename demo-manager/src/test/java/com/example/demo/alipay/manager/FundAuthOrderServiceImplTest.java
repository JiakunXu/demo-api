package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FundAuthOrderServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void freeze0BuildsRequestAndReturnsResult() throws Exception {
        String outOrderNo = "outOrderNo-value";
        String outRequestNo = "outRequestNo-value";
        String orderTitle = "orderTitle-value";
        BigDecimal amount = new BigDecimal("12.34");
        String timeoutExpress = "timeoutExpress-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("out_order_no", outOrderNo);
        expectedBiz.put("out_request_no", outRequestNo);
        expectedBiz.put("order_title", orderTitle);
        expectedBiz.put("amount", amount);
        expectedBiz.put("product_code", "PRE_AUTH_ONLINE");
        expectedBiz.put("timeout_express", timeoutExpress);

        when(client.execute(eq("alipay.fund.auth.order.app.freeze"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.freeze(outOrderNo, outRequestNo, orderTitle, amount, timeoutExpress)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("alipay.fund.auth.order.app.freeze"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);
            verify(client).asyncNotify("https://callback.test");
        }
    }

    @Test
    void freeze0PreservesSdkFailure() throws Exception {
        String outOrderNo = "outOrderNo-value";
        String outRequestNo = "outRequestNo-value";
        String orderTitle = "orderTitle-value";
        BigDecimal amount = new BigDecimal("12.34");
        String timeoutExpress = "timeoutExpress-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("alipay.fund.auth.order.app.freeze"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.freeze(outOrderNo, outRequestNo, orderTitle, amount, timeoutExpress)).hasCause(failure);
        }
    }

    @Test
    void freeze0RejectsBusinessError() throws Exception {
        String outOrderNo = "outOrderNo-value";
        String outRequestNo = "outRequestNo-value";
        String orderTitle = "orderTitle-value";
        BigDecimal amount = new BigDecimal("12.34");
        String timeoutExpress = "timeoutExpress-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("alipay.fund.auth.order.app.freeze"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.freeze(outOrderNo, outRequestNo, orderTitle, amount, timeoutExpress)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void unfreeze1BuildsRequestAndReturnsResult() throws Exception {
        String authNo = "authNo-value";
        String outRequestNo = "outRequestNo-value";
        BigDecimal amount = new BigDecimal("12.34");
        String remark = "remark-value";
        String extraParam = "extraParam-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("auth_no", authNo);
        expectedBiz.put("out_request_no", outRequestNo);
        expectedBiz.put("amount", amount);
        expectedBiz.put("remark", remark);
        expectedBiz.put("extra_param", extraParam);

        when(client.execute(eq("alipay.fund.auth.order.unfreeze"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.unfreeze(authNo, outRequestNo, amount, remark, extraParam)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("alipay.fund.auth.order.unfreeze"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);
            verify(client).asyncNotify("https://callback.test");
        }
    }

    @Test
    void unfreeze1PreservesSdkFailure() throws Exception {
        String authNo = "authNo-value";
        String outRequestNo = "outRequestNo-value";
        BigDecimal amount = new BigDecimal("12.34");
        String remark = "remark-value";
        String extraParam = "extraParam-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("alipay.fund.auth.order.unfreeze"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.unfreeze(authNo, outRequestNo, amount, remark, extraParam)).hasCause(failure);
        }
    }

    @Test
    void unfreeze1RejectsBusinessError() throws Exception {
        String authNo = "authNo-value";
        String outRequestNo = "outRequestNo-value";
        BigDecimal amount = new BigDecimal("12.34");
        String remark = "remark-value";
        String extraParam = "extraParam-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("alipay.fund.auth.order.unfreeze"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.unfreeze(authNo, outRequestNo, amount, remark, extraParam)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void query2BuildsRequestAndReturnsResult() throws Exception {
        String authNo = "authNo-value";
        String outOrderNo = "outOrderNo-value";
        String operationId = "operationId-value";
        String outRequestNo = "outRequestNo-value";
        String operationType = "operationType-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("auth_no", authNo);
        expectedBiz.put("out_order_no", outOrderNo);
        expectedBiz.put("operation_id", operationId);
        expectedBiz.put("out_request_no", outRequestNo);
        expectedBiz.put("operation_type", operationType);

        when(client.execute(eq("alipay.fund.auth.operation.detail.query"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.query(authNo, outOrderNo, operationId, outRequestNo, operationType)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("alipay.fund.auth.operation.detail.query"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);

        }
    }

    @Test
    void query2PreservesSdkFailure() throws Exception {
        String authNo = "authNo-value";
        String outOrderNo = "outOrderNo-value";
        String operationId = "operationId-value";
        String outRequestNo = "outRequestNo-value";
        String operationType = "operationType-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("alipay.fund.auth.operation.detail.query"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.query(authNo, outOrderNo, operationId, outRequestNo, operationType)).hasCause(failure);
        }
    }

    @Test
    void query2RejectsBusinessError() throws Exception {
        String authNo = "authNo-value";
        String outOrderNo = "outOrderNo-value";
        String operationId = "operationId-value";
        String outRequestNo = "outRequestNo-value";
        String operationType = "operationType-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("alipay.fund.auth.operation.detail.query"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.query(authNo, outOrderNo, operationId, outRequestNo, operationType)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void cancel3BuildsRequestAndReturnsResult() throws Exception {
        String authNo = "authNo-value";
        String outOrderNo = "outOrderNo-value";
        String operationId = "operationId-value";
        String outRequestNo = "outRequestNo-value";
        String remark = "remark-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("auth_no", authNo);
        expectedBiz.put("out_order_no", outOrderNo);
        expectedBiz.put("operation_id", operationId);
        expectedBiz.put("out_request_no", outRequestNo);
        expectedBiz.put("remark", remark);

        when(client.execute(eq("alipay.fund.auth.operation.detail.query"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.cancel(authNo, outOrderNo, operationId, outRequestNo, remark)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("alipay.fund.auth.operation.detail.query"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);
            verify(client).asyncNotify("https://callback.test");
        }
    }

    @Test
    void cancel3PreservesSdkFailure() throws Exception {
        String authNo = "authNo-value";
        String outOrderNo = "outOrderNo-value";
        String operationId = "operationId-value";
        String outRequestNo = "outRequestNo-value";
        String remark = "remark-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("alipay.fund.auth.operation.detail.query"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.cancel(authNo, outOrderNo, operationId, outRequestNo, remark)).hasCause(failure);
        }
    }

    @Test
    void cancel3RejectsBusinessError() throws Exception {
        String authNo = "authNo-value";
        String outOrderNo = "outOrderNo-value";
        String operationId = "operationId-value";
        String outRequestNo = "outRequestNo-value";
        String remark = "remark-value";
        FundAuthOrderServiceImpl target = new FundAuthOrderServiceImpl();
        ReflectionTestUtils.setField(target, "notifyUrl", "https://callback.test");
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("alipay.fund.auth.operation.detail.query"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.cancel(authNo, outOrderNo, operationId, outRequestNo, remark)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }
}
