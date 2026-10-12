package com.example.demo.wxpay.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.wxpay.api.*;
import com.example.demo.wxpay.api.bo.*;
import com.example.demo.wxpay.dao.dataobject.*;
import com.example.demo.wxpay.dao.mapper.*;
import com.example.demo.support.ServiceTestSupport;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class WxpayRefundServiceImplTest {
    private final WxpayRefundServiceImpl service = spy(new WxpayRefundServiceImpl());
    private final RSAAutoCertificateConfig merchant = mock(RSAAutoCertificateConfig.class);
    private final RSAAutoCertificateConfig partner = mock(RSAAutoCertificateConfig.class);
    private final RefundService remote = mock(RefundService.class);
    @BeforeEach
    void initialize() {
        ServiceTestSupport.mapper(service, mock(WxpayRefundMapper.class), WxpayRefundDO.class);
        ReflectionTestUtils.setField(service, "merchantConfig", merchant);
        ReflectionTestUtils.setField(service, "partnerConfig", partner);
        ReflectionTestUtils.setField(service, "refundService", remote);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void refundQueryMapsIdentifiersAndAmountsForBothMerchantModes(boolean subMerchant) {
        var response = JSON.parseObject("{\"refund_id\":\"refund\",\"out_refund_no\":\"refund-order\",\"transaction_id\":\"transaction\",\"out_trade_no\":\"order\",\"status\":\"SUCCESS\",\"refund_status\":\"SUCCESS\",\"amount\":{\"refund\":1230,\"total\":1230}}", com.wechat.pay.java.service.refund.model.Refund.class);
        if (subMerchant) when(remote.queryByOutRefundNo("sub", "refund-order")).thenReturn(response);
        else when(remote.queryByOutRefundNo("refund-order")).thenReturn(response);
        var result = subMerchant ? service.getRefund("sub", "refund-order") : service.getRefund("refund-order");
        assertThat(result.getRefundId()).isEqualTo("refund");
        assertThat(result.getOutRefundNo()).isEqualTo("refund-order");
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
        assertThat(result.getChannel()).isNull();
        assertThat(JSON.parseObject(result.getAmount()).getIntValue("refund")).isEqualTo(1230);
    }

    @Test
    void missingRefundIdentifiersDoNotCallProvider() {
        assertThat(service.getRefund(" ")).isNull();
        assertThat(service.getRefund("", "refund")).isNull();
        verifyNoInteractions(remote);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void refundCallbackUsesSelectedMerchantConfig(boolean subMerchant) {
        var response = JSON.parseObject("{\"refund_id\":\"refund\",\"out_refund_no\":\"refund-order\",\"transaction_id\":\"transaction\",\"out_trade_no\":\"order\",\"status\":\"SUCCESS\",\"refund_status\":\"SUCCESS\",\"amount\":{\"refund\":1230,\"total\":1230}}", com.wechat.pay.java.service.refund.model.RefundNotification.class);
        try (var parsers = mockConstruction(NotificationParser.class, (parser, context) -> {
            assertThat((Object[]) context.arguments().getFirst()).containsExactly(subMerchant ? partner : merchant);
            when(parser.parse(any(RequestParam.class), eq(com.wechat.pay.java.service.refund.model.RefundNotification.class))).thenReturn(response);
        })) {
            var result = subMerchant ? service.getRefundV2("serial", "nonce", "123", "sign", "body") : service.getRefundV1("serial", "nonce", "123", "sign", "body");
            assertThat(result.getRefundId()).isEqualTo("refund"); assertThat(result.getStatus()).isEqualTo("SUCCESS");
            verify(parsers.constructed().getFirst()).parse(argThat(request -> "serial".equals(request.getSerialNumber()) && "body".equals(request.getBody()) && "sign".equals(request.getSignature()) && "123\nnonce\nbody\n".equals(request.getMessage())), eq(com.wechat.pay.java.service.refund.model.RefundNotification.class));
        }
    }

    @Test
    void parsingFailureIsNotAcceptedAsSuccessfulCallback() {
        RuntimeException failure = new RuntimeException("invalid signature");
        try (var parsers = mockConstruction(NotificationParser.class, (parser, context) -> when(parser.parse(any(RequestParam.class), any(Class.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> service.getRefundV1("serial", "nonce", "123", "sign", "{}")).isSameAs(failure);
        }
    }

    @Test
    void persistenceStoresBusinessIdentifiersAndSnapshot() {
        doReturn(true).when(service).save(any(WxpayRefundDO.class));
        WxpayRefund data = new WxpayRefund(); data.setOutTradeNo("order");
        data.setOutRefundNo("refund-order");
        assertThat(service.insertRefund(data)).isSameAs(data);
        var capture = ArgumentCaptor.forClass(WxpayRefundDO.class); verify(service).save(capture.capture());
        assertThat(capture.getValue().getOutTradeNo()).isEqualTo("order");
        assertThat(capture.getValue().getCreator()).isEqualTo("系统");
        assertThat(JSON.parseObject(capture.getValue().getRefund(), WxpayRefund.class).getOutTradeNo()).isEqualTo("order");
    }

}
