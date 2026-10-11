package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.example.demo.alipay.api.bo.trade.TradeOrderPayResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TradeOrderServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void pay0BuildsRequestAndReturnsResult() throws Exception {
        String tradeNo = "tradeNo-value";
        TradeOrderServiceImpl target = new TradeOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        response.httpBody = "{\"alipay_trade_order_pay_response\": {\"code\": \"10000\", \"user_id\": \"user-1\", \"access_token\": \"token-1\", \"order_id\": \"order-1\", \"out_biz_no\": \"business-1\"}}";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("trade_no", tradeNo);

        when(client.execute(eq("alipay.trade.order.pay"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.pay(tradeNo)).usingRecursiveComparison().isEqualTo(JSON.parseObject("{\"code\":\"10000\",\"user_id\":\"user-1\",\"access_token\":\"token-1\",\"order_id\":\"order-1\",\"out_biz_no\":\"business-1\"}", TradeOrderPayResponse.class));
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("alipay.trade.order.pay"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);

        }
    }

    @Test
    void pay0PreservesSdkFailure() throws Exception {
        String tradeNo = "tradeNo-value";
        TradeOrderServiceImpl target = new TradeOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("alipay.trade.order.pay"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.pay(tradeNo)).hasCause(failure);
        }
    }

    @Test
    void pay0RejectsBusinessError() throws Exception {
        String tradeNo = "tradeNo-value";
        TradeOrderServiceImpl target = new TradeOrderServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("alipay.trade.order.pay"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.pay(tradeNo)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }
}
