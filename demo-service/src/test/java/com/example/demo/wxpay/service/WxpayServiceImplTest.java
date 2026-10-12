package com.example.demo.wxpay.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.wxpay.api.*;
import com.example.demo.wxpay.api.bo.*;
import com.example.demo.wxpay.dao.dataobject.*;
import com.example.demo.wxpay.dao.mapper.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class WxpayServiceImplTest {
    private final JsapiService merchant = mock(JsapiService.class);
    private final PartnerJsapiService partner = mock(PartnerJsapiService.class);
    private final WxpayServiceImpl service = new WxpayServiceImpl();
    @BeforeEach
    void initialize() {
        ReflectionTestUtils.setField(service, "jsapiService", merchant);
        ReflectionTestUtils.setField(service, "partnerJsapiService", partner);
        for (String field : new String[] {"appid", "mchid", "notifyUrl", "spAppid", "spMchid", "spNotifyUrl"}) ReflectionTestUtils.setField(service, field, field);
    }

    @Test
    void merchantBuildUsesConfiguredAppMerchantAndNotifyUrl() {
        var response = new com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse();
        when(merchant.prepayWithRequestPayment("appid", "mchid", "description", "order", "time", "attach", "notifyUrl", 1230, "openid")).thenReturn(response);
        assertThat(service.build("description", "order", "time", "attach", 1230, "openid")).isEqualTo(JSON.toJSONString(response));
        verify(merchant).prepayWithRequestPayment("appid", "mchid", "description", "order", "time", "attach", "notifyUrl", 1230, "openid");
        verifyNoInteractions(partner);
    }

    @Test
    void partnerBuildUsesSubMerchantAndPartnerConfig() {
        var response = new com.wechat.pay.java.service.partnerpayments.jsapi.model.PrepayWithRequestPaymentResponse();
        when(partner.prepayWithRequestPayment("spAppid", "spMchid", "sub", "description", "order", "time", "attach", "spNotifyUrl", 1230, "openid")).thenReturn(response);
        assertThat(service.build("sub", "description", "order", "time", "attach", 1230, "openid")).isEqualTo(JSON.toJSONString(response));
        verifyNoInteractions(merchant);
    }

    @Test
    void closeRoutesToCorrectMerchantService() { service.close("order");
    service.close("sub", "order");
    verify(merchant).closeOrder("mchid", "order");
    verify(partner).closeOrder("spMchid", "sub", "order");
    }

    @Test
    void closeFailurePropagates() { doThrow(new RuntimeException("provider failed")).when(merchant).closeOrder("mchid", "order");
    assertThatThrownBy(() -> service.close("order")).hasMessage("provider failed");
    }

}
