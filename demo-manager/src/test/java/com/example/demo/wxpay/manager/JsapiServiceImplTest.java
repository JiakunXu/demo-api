package com.example.demo.wxpay.manager;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsapiServiceImplTest {
    @Test
    void prepayWithRequestPayment1BuildsRequest() {
        String appid = "appid-value";
        String mchid = "mchid-value";
        String description = "description-value";
        String outTradeNo = "outTradeNo-value";
        String timeExpire = "timeExpire-value";
        String attach = "attach-value";
        String notifyUrl = "notifyUrl-value";
        Integer total = 120;
        String openid = "openid-value";
        JsapiServiceExtension client = mock(JsapiServiceExtension.class);
        JsapiServiceImpl target = new JsapiServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "merchantConfig", config);
        PrepayWithRequestPaymentResponse response = new PrepayWithRequestPaymentResponse();
        when(client.prepayWithRequestPayment(any(PrepayRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(JsapiServiceExtension.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.prepayWithRequestPayment(appid, mchid, description, outTradeNo, timeExpire, attach, notifyUrl, total, openid)).isSameAs(response);
            ArgumentCaptor<PrepayRequest> requestCaptor = ArgumentCaptor.forClass(PrepayRequest.class);
            verify(client).prepayWithRequestPayment(requestCaptor.capture());
            PrepayRequest request = requestCaptor.getValue();
            assertThat(request.getAppid()).isEqualTo(appid);
            assertThat(request.getMchid()).isEqualTo(mchid);
            assertThat(request.getDescription()).isEqualTo(description);
            assertThat(request.getOutTradeNo()).isEqualTo(outTradeNo);
            assertThat(request.getTimeExpire()).isEqualTo(timeExpire);
            assertThat(request.getAttach()).isEqualTo(attach);
            assertThat(request.getNotifyUrl()).isEqualTo(notifyUrl);
            assertThat(request.getAmount().getTotal()).isEqualTo(total);
            assertThat(request.getPayer().getOpenid()).isEqualTo(openid);
            assertThat(request.getSettleInfo().getProfitSharing()).isTrue();
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void prepayWithRequestPayment1ConvertsSdkErrors(String kind) {
        String appid = "appid-value";
        String mchid = "mchid-value";
        String description = "description-value";
        String outTradeNo = "outTradeNo-value";
        String timeExpire = "timeExpire-value";
        String attach = "attach-value";
        String notifyUrl = "notifyUrl-value";
        Integer total = 120;
        String openid = "openid-value";
        JsapiServiceExtension client = mock(JsapiServiceExtension.class);
        JsapiServiceImpl target = new JsapiServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.prepayWithRequestPayment(any(PrepayRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(JsapiServiceExtension.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.prepayWithRequestPayment(appid, mchid, description, outTradeNo, timeExpire, attach, notifyUrl, total, openid)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).prepayWithRequestPayment(any(PrepayRequest.class));
        }
    }

    @Test
    void closeOrder2BuildsRequest() {
        String mchid = "mchid-value";
        String outTradeNo = "outTradeNo-value";
        com.wechat.pay.java.service.payments.jsapi.JsapiService client = mock(com.wechat.pay.java.service.payments.jsapi.JsapiService.class);
        JsapiServiceImpl target = new JsapiServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "merchantConfig", config);

        try (var builders = mockConstruction(com.wechat.pay.java.service.payments.jsapi.JsapiService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            target.closeOrder(mchid, outTradeNo);
            ArgumentCaptor<CloseOrderRequest> requestCaptor = ArgumentCaptor.forClass(CloseOrderRequest.class);
            verify(client).closeOrder(requestCaptor.capture());
            CloseOrderRequest request = requestCaptor.getValue();
            assertThat(request.getMchid()).isEqualTo(mchid); assertThat(request.getOutTradeNo()).isEqualTo(outTradeNo);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void closeOrder2ConvertsSdkErrors(String kind) {
        String mchid = "mchid-value";
        String outTradeNo = "outTradeNo-value";
        com.wechat.pay.java.service.payments.jsapi.JsapiService client = mock(com.wechat.pay.java.service.payments.jsapi.JsapiService.class);
        JsapiServiceImpl target = new JsapiServiceImpl();
        RuntimeException failure = failure(kind);
        doThrow(failure).when(client).closeOrder(any(CloseOrderRequest.class));
        try (var builders = mockConstruction(com.wechat.pay.java.service.payments.jsapi.JsapiService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.closeOrder(mchid, outTradeNo)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).closeOrder(any(CloseOrderRequest.class));
        }
    }

    @Test
    void queryOrderByOutTradeNo3BuildsRequest() {
        String mchid = "mchid-value";
        String outTradeNo = "outTradeNo-value";
        com.wechat.pay.java.service.payments.jsapi.JsapiService client = mock(com.wechat.pay.java.service.payments.jsapi.JsapiService.class);
        JsapiServiceImpl target = new JsapiServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "merchantConfig", config);
        Transaction response = new Transaction();
        when(client.queryOrderByOutTradeNo(any(QueryOrderByOutTradeNoRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.payments.jsapi.JsapiService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.queryOrderByOutTradeNo(mchid, outTradeNo)).isSameAs(response);
            ArgumentCaptor<QueryOrderByOutTradeNoRequest> requestCaptor = ArgumentCaptor.forClass(QueryOrderByOutTradeNoRequest.class);
            verify(client).queryOrderByOutTradeNo(requestCaptor.capture());
            QueryOrderByOutTradeNoRequest request = requestCaptor.getValue();
            assertThat(request.getMchid()).isEqualTo(mchid); assertThat(request.getOutTradeNo()).isEqualTo(outTradeNo);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void queryOrderByOutTradeNo3ConvertsSdkErrors(String kind) {
        String mchid = "mchid-value";
        String outTradeNo = "outTradeNo-value";
        com.wechat.pay.java.service.payments.jsapi.JsapiService client = mock(com.wechat.pay.java.service.payments.jsapi.JsapiService.class);
        JsapiServiceImpl target = new JsapiServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.queryOrderByOutTradeNo(any(QueryOrderByOutTradeNoRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.payments.jsapi.JsapiService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.queryOrderByOutTradeNo(mchid, outTradeNo)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).queryOrderByOutTradeNo(any(QueryOrderByOutTradeNoRequest.class));
        }
    }

    private static RuntimeException failure(String kind) {
        if (kind.equals("http")) { return mock(HttpException.class); }
        if (kind.equals("service")) {
            ServiceException error = mock(ServiceException.class);
            when(error.getErrorMessage()).thenReturn("provider rejected");
            return error;
        }
        MalformedMessageException error = mock(MalformedMessageException.class);
        when(error.getMessage()).thenReturn("provider rejected");
        return error;
    }
}
