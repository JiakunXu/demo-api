package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TradeSettleServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void confirm0BuildsRequestAndReturnsResult() throws Exception {
        String outRequestNo = "outRequestNo-value";
        String tradeNo = "tradeNo-value";
        TradeSettleServiceImpl target = new TradeSettleServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("out_request_no", outRequestNo);
        expectedBiz.put("trade_no", tradeNo);

        when(client.execute(eq("alipay.trade.create"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.confirm(outRequestNo, tradeNo)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("alipay.trade.create"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);

        }
    }

    @Test
    void confirm0PreservesSdkFailure() throws Exception {
        String outRequestNo = "outRequestNo-value";
        String tradeNo = "tradeNo-value";
        TradeSettleServiceImpl target = new TradeSettleServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("alipay.trade.create"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.confirm(outRequestNo, tradeNo)).hasCause(failure);
        }
    }

    @Test
    void confirm0RejectsBusinessError() throws Exception {
        String outRequestNo = "outRequestNo-value";
        String tradeNo = "tradeNo-value";
        TradeSettleServiceImpl target = new TradeSettleServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("alipay.trade.create"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.confirm(outRequestNo, tradeNo)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }
}
