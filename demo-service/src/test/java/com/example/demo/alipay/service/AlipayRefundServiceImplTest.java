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
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlipayRefundServiceImplTest {
    private final AlipayRefundServiceImpl service = spy(new AlipayRefundServiceImpl());
    private final AlipayService remote = mock(AlipayService.class);
    @BeforeEach
    void initialize() { ServiceTestSupport.mapper(service, mock(AlipayRefundMapper.class), AlipayRefundDO.class);
    ReflectionTestUtils.setField(service, "alipayService", remote);
    }

    @Test
    void directRefundResponseAndQueryResponseMapDistinctFields() {
        var response = new AlipayTradeRefundResponse();
        response.tradeNo = "trade";
        response.outTradeNo = "order";
        response.fundChange = "Y";
        assertThat(service.getRefund(response).getFundChange()).isEqualTo("Y");
        var query = new AlipayTradeFastpayRefundQueryResponse();
        query.tradeNo = "trade";
        query.outTradeNo = "order";
        query.outRequestNo = "refund";
        query.refundAmount = "12.30";
        query.refundStatus = "REFUND_SUCCESS";
        when(remote.queryRefund("order", "refund")).thenReturn(query);
        assertThat(service.getRefund(query).getRefundAmount()).isEqualTo("12.30");
        assertThat(service.getRefund("order", "refund").getOutRequestNo()).isEqualTo("refund");
        assertThat(service.getRefund((AlipayTradeRefundResponse) null)).isNull();
        assertThat(service.getRefund((AlipayTradeFastpayRefundQueryResponse) null)).isNull();
    }

    @Test
    void unverifiedRefundNotificationIsRejected() throws Exception {
        Client client = mock(Client.class);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            assertThatThrownBy(() -> service.getRefund(Map.of())).hasMessageContaining("验签失败");
            verify(client).verifyNotify(Map.of());
        }
    }

    @Test
    void persistenceStoresOrderRefundIdentifiersAndSnapshot() {
        doReturn(true).when(service).save(any(AlipayRefundDO.class));
        AlipayRefund refund = new AlipayRefund(); refund.setOutTradeNo("order"); refund.setOutRequestNo("refund");
        assertThat(service.insertRefund(refund)).isSameAs(refund);
        var capture = ArgumentCaptor.forClass(AlipayRefundDO.class); verify(service).save(capture.capture());
        assertThat(capture.getValue().getOutTradeNo()).isEqualTo("order");
        assertThat(capture.getValue().getOutRequestNo()).isEqualTo("refund");
        assertThat(capture.getValue().getCreator()).isEqualTo("sys");
        assertThat(JSON.parseObject(capture.getValue().getRefund(), AlipayRefund.class).getOutRequestNo()).isEqualTo("refund");
    }
}
