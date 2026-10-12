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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class WxpayTradeServiceImplTest {
    private final WxpayTradeServiceImpl service = spy(new WxpayTradeServiceImpl());
    private final RSAAutoCertificateConfig merchant = mock(RSAAutoCertificateConfig.class);
    private final RSAAutoCertificateConfig partner = mock(RSAAutoCertificateConfig.class);
    private final JsapiService remote = mock(JsapiService.class);
    private final PartnerJsapiService partnerRemote = mock(PartnerJsapiService.class);
    @BeforeEach
    void initialize() {
        ServiceTestSupport.mapper(service, mock(WxpayTradeMapper.class), WxpayTradeDO.class);
        ReflectionTestUtils.setField(service, "merchantConfig", merchant);
        ReflectionTestUtils.setField(service, "partnerConfig", partner);
        ReflectionTestUtils.setField(service, "jsapiService", remote);
        ReflectionTestUtils.setField(service, "partnerJsapiService", partnerRemote);
    }

    @Test
    void merchantQueryMapsIdentifiersStateAndAmounts() {
        var response = JSON.parseObject("{\"appid\":\"app\",\"mchid\":\"merchant\",\"sp_appid\":\"sp-app\",\"sp_mchid\":\"sp-merchant\",\"sub_mchid\":\"sub\",\"transaction_id\":\"transaction\",\"out_trade_no\":\"order\",\"trade_state\":\"SUCCESS\",\"payer\":{\"openid\":\"user\"},\"amount\":{\"total\":1230}}", com.wechat.pay.java.service.payments.model.Transaction.class);
        when(remote.queryOrderByOutTradeNo("merchant", "order")).thenReturn(response);
        var result = service.getTrade("merchant", "order");
        assertThat(result.getTransactionId()).isEqualTo("transaction");
        assertThat(result.getOutTradeNo()).isEqualTo("order");
        assertThat(result.getTradeState()).isEqualTo("SUCCESS");
        assertThat(result.getTradeType()).isNull();
        assertThat(JSON.parseObject(result.getAmount()).getIntValue("total")).isEqualTo(1230);
        verify(remote).queryOrderByOutTradeNo("merchant", "order");
    }

    @Test
    void partnerQueryMapsIdentifiersStateAndAmounts() {
        var response = JSON.parseObject("{\"appid\":\"app\",\"mchid\":\"merchant\",\"sp_appid\":\"sp-app\",\"sp_mchid\":\"sp-merchant\",\"sub_mchid\":\"sub\",\"transaction_id\":\"transaction\",\"out_trade_no\":\"order\",\"trade_state\":\"SUCCESS\",\"payer\":{\"openid\":\"user\"},\"amount\":{\"total\":1230}}", com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction.class);
        when(partnerRemote.queryOrderByOutTradeNo("sp", "sub", "order")).thenReturn(response);
        var result = service.getTrade("sp", "sub", "order");
        assertThat(result.getTransactionId()).isEqualTo("transaction");
        assertThat(result.getOutTradeNo()).isEqualTo("order");
        assertThat(result.getTradeState()).isEqualTo("SUCCESS");
        assertThat(result.getTradeType()).isNull();
        assertThat(JSON.parseObject(result.getAmount()).getIntValue("total")).isEqualTo(1230);
        verify(partnerRemote).queryOrderByOutTradeNo("sp", "sub", "order");
    }

    @Test
    void callbackV1UsesCorrectConfigAndPassesAllSignatureFields() {
        var response = JSON.parseObject("{\"appid\":\"app\",\"mchid\":\"merchant\",\"sp_appid\":\"sp-app\",\"sp_mchid\":\"sp-merchant\",\"sub_mchid\":\"sub\",\"transaction_id\":\"transaction\",\"out_trade_no\":\"order\",\"trade_state\":\"SUCCESS\",\"payer\":{\"openid\":\"user\"},\"amount\":{\"total\":1230}}", com.wechat.pay.java.service.payments.model.Transaction.class);
        try (var parsers = mockConstruction(NotificationParser.class, (parser, context) -> {
            assertThat((Object[]) context.arguments().getFirst()).containsExactly(merchant);
            when(parser.parse(any(RequestParam.class), eq(com.wechat.pay.java.service.payments.model.Transaction.class))).thenReturn(response);
        })) {
            String payload = "{\"event_type\":\"TRANSACTION.SUCCESS\",\"summary\":\"paid\"}";
            var result = service.getTradeV1("serial", "nonce", "123", "sign", payload);
            assertThat(result.getTransactionId()).isEqualTo("transaction");
            assertThat(result.getEventType()).isEqualTo("TRANSACTION.SUCCESS");
            var request = ArgumentCaptor.forClass(RequestParam.class);
            verify(parsers.constructed().getFirst()).parse(request.capture(), eq(com.wechat.pay.java.service.payments.model.Transaction.class));
            assertThat(request.getValue().getSerialNumber()).isEqualTo("serial");
            assertThat(request.getValue().getSignature()).isEqualTo("sign");
            assertThat(request.getValue().getMessage()).isEqualTo("123\nnonce\n" + payload + "\n");
            assertThat(request.getValue().getBody()).isEqualTo(payload);
        }
    }

    @Test
    void callbackV2UsesCorrectConfigAndPassesAllSignatureFields() {
        var response = JSON.parseObject("{\"appid\":\"app\",\"mchid\":\"merchant\",\"sp_appid\":\"sp-app\",\"sp_mchid\":\"sp-merchant\",\"sub_mchid\":\"sub\",\"transaction_id\":\"transaction\",\"out_trade_no\":\"order\",\"trade_state\":\"SUCCESS\",\"payer\":{\"openid\":\"user\"},\"amount\":{\"total\":1230}}", com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction.class);
        try (var parsers = mockConstruction(NotificationParser.class, (parser, context) -> {
            assertThat((Object[]) context.arguments().getFirst()).containsExactly(partner);
            when(parser.parse(any(RequestParam.class), eq(com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction.class))).thenReturn(response);
        })) {
            String payload = "{\"event_type\":\"TRANSACTION.SUCCESS\",\"summary\":\"paid\"}";
            var result = service.getTradeV2("serial", "nonce", "123", "sign", payload);
            assertThat(result.getTransactionId()).isEqualTo("transaction");
            assertThat(result.getEventType()).isEqualTo("TRANSACTION.SUCCESS");
            var request = ArgumentCaptor.forClass(RequestParam.class);
            verify(parsers.constructed().getFirst()).parse(request.capture(), eq(com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction.class));
            assertThat(request.getValue().getSerialNumber()).isEqualTo("serial");
            assertThat(request.getValue().getSignature()).isEqualTo("sign");
            assertThat(request.getValue().getMessage()).isEqualTo("123\nnonce\n" + payload + "\n");
            assertThat(request.getValue().getBody()).isEqualTo(payload);
        }
    }

    @Test
    void parsingFailureIsNotAcceptedAsSuccessfulCallback() {
        RuntimeException failure = new RuntimeException("invalid signature");
        try (var parsers = mockConstruction(NotificationParser.class, (parser, context) -> when(parser.parse(any(RequestParam.class), any(Class.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> service.getTradeV1("serial", "nonce", "123", "sign", "{}")).isSameAs(failure);
        }
    }

    @Test
    void persistenceStoresBusinessIdentifiersAndSnapshot() {
        doReturn(true).when(service).save(any(WxpayTradeDO.class));
        WxpayTrade data = new WxpayTrade(); data.setOutTradeNo("order");
        assertThat(service.insertTrade(data)).isSameAs(data);
        var capture = ArgumentCaptor.forClass(WxpayTradeDO.class); verify(service).save(capture.capture());
        assertThat(capture.getValue().getOutTradeNo()).isEqualTo("order");
        assertThat(capture.getValue().getCreator()).isEqualTo("系统");
        assertThat(JSON.parseObject(capture.getValue().getTrade(), WxpayTrade.class).getOutTradeNo()).isEqualTo("order");
    }

}
