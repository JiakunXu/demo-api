package com.example.demo.alipay.service;

import com.alibaba.fastjson2.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.Client;
import com.alipay.easysdk.payment.common.models.*;
import com.example.demo.alipay.api.*;
import com.example.demo.alipay.api.bo.*;
import com.example.demo.alipay.api.bo.fund.*;
import com.example.demo.alipay.dao.dataobject.*;
import com.example.demo.alipay.dao.mapper.*;
import com.example.demo.support.ServiceTestSupport;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlipayTradeServiceImplTest {
    private final AlipayTradeServiceImpl service = spy(new AlipayTradeServiceImpl());
    private final FactoryPaymentCommonService remote = mock(FactoryPaymentCommonService.class);
    @BeforeEach
    void initialize() { ServiceTestSupport.mapper(service, mock(AlipayTradeMapper.class), AlipayTradeDO.class);
    ReflectionTestUtils.setField(service, "factoryPaymentCommonService", remote);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void notificationsRequireSignatureAndMapMonetaryAmounts(boolean valid) throws Exception {
        Client client = mock(Client.class);
        Map<String, String> input = Map.of("out_trade_no", "order", "trade_no", "trade", "total_amount", "12.30", "refund_fee", "0.20", "notify_time", "2024-02-29 12:34:56", "gmt_create", "", "trade_status", "TRADE_SUCCESS");
        when(client.verifyNotify(input)).thenReturn(valid);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            if (valid) {
                var result = service.getTrade(input);
                assertThat(result.getOutTradeNo()).isEqualTo("order");
                assertThat(result.getTradeNo()).isEqualTo("trade");
                assertThat(result.getTotalAmount()).isEqualByComparingTo("12.30");
                assertThat(result.getRefundFee()).isEqualByComparingTo("0.20");
                assertThat(result.getNotifyTime()).isNotNull();
                assertThat(result.getGmtCreate()).isNull();
                assertThat(result.getReceiptAmount()).isNull();
            } else assertThatThrownBy(() -> service.getTrade(input)).hasMessageContaining("验签失败");
            verify(client).verifyNotify(input);
        }
    }

    @Test
    void queryConvertsSdkAmountsAndGeneratesNotificationId() {
        AlipayTradeQueryResponse response = new AlipayTradeQueryResponse();
        response.outTradeNo = "order";
        response.tradeNo = "trade";
        response.totalAmount = "12.30";
        response.tradeStatus = "TRADE_SUCCESS";
        when(remote.query("auth", "order")).thenReturn(response);
        var result = service.getTrade("auth", "order");
        assertThat(result.getTotalAmount()).isEqualByComparingTo("12.30");
        assertThat(result.getNotifyId()).matches("[0-9a-f-]{36}");
        assertThat(result.getTradeNo()).isEqualTo("trade");
        verify(remote).query("auth", "order");
    }

    @Test
    void insertionStoresBusinessIdentifierAndSnapshot() {
        doReturn(true).when(service).save(any(AlipayTradeDO.class));
        AlipayTrade trade = new AlipayTrade(); trade.setOutTradeNo("order"); trade.setTradeStatus("TRADE_SUCCESS");
        assertThat(service.insertTrade(trade)).isSameAs(trade);
        var capture = ArgumentCaptor.forClass(AlipayTradeDO.class); verify(service).save(capture.capture());
        assertThat(capture.getValue().getOutTradeNo()).isEqualTo("order");
        assertThat(capture.getValue().getCreator()).isEqualTo("系统");
        assertThat(JSON.parseObject(capture.getValue().getTrade(), AlipayTrade.class).getTradeStatus()).isEqualTo("TRADE_SUCCESS");
    }
}
