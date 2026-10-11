package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.example.demo.alipay.api.bo.fund.FundTransUniTransferResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FundTransServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void transfer0BuildsRequestAndReturnsResult() throws Exception {
        String outBizNo = "outBizNo-value";
        BigDecimal transAmount = new BigDecimal("12.34");
        String orderTitle = "orderTitle-value";
        String userId = "userId-value";
        FundTransServiceImpl target = new FundTransServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        response.httpBody = "{\"alipay_fund_trans_uni_transfer_response\": {\"code\": \"10000\", \"user_id\": \"user-1\", \"access_token\": \"token-1\", \"order_id\": \"order-1\", \"out_biz_no\": \"business-1\"}}";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("out_biz_no", outBizNo);
        expectedBiz.put("trans_amount", transAmount);
        expectedBiz.put("biz_scene", "DIRECT_TRANSFER");
        expectedBiz.put("product_code", "TRANS_ACCOUNT_NO_PWD");
        expectedBiz.put("order_title", orderTitle);

        when(client.execute(eq("alipay.fund.trans.uni.transfer"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.transfer(outBizNo, transAmount, orderTitle, userId)).usingRecursiveComparison().isEqualTo(JSON.parseObject("{\"code\":\"10000\",\"user_id\":\"user-1\",\"access_token\":\"token-1\",\"order_id\":\"order-1\",\"out_biz_no\":\"business-1\"}", FundTransUniTransferResponse.class));
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("alipay.fund.trans.uni.transfer"), isNull(), biz.capture());
            assertThat(ReflectionTestUtils.getField(biz.getValue().get("payee_info"), "identity")).isEqualTo(userId);
            assertThat(ReflectionTestUtils.getField(biz.getValue().get("payee_info"), "identityType")).isEqualTo("ALIPAY_USER_ID");
            assertThat(biz.getValue()).containsAllEntriesOf(expectedBiz);

        }
    }

    @Test
    void transfer0PreservesSdkFailure() throws Exception {
        String outBizNo = "outBizNo-value";
        BigDecimal transAmount = new BigDecimal("12.34");
        String orderTitle = "orderTitle-value";
        String userId = "userId-value";
        FundTransServiceImpl target = new FundTransServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("alipay.fund.trans.uni.transfer"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.transfer(outBizNo, transAmount, orderTitle, userId)).hasCause(failure);
        }
    }

    @Test
    void transfer0RejectsBusinessError() throws Exception {
        String outBizNo = "outBizNo-value";
        BigDecimal transAmount = new BigDecimal("12.34");
        String orderTitle = "orderTitle-value";
        String userId = "userId-value";
        FundTransServiceImpl target = new FundTransServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("alipay.fund.trans.uni.transfer"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.transfer(outBizNo, transAmount, orderTitle, userId)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }
}
