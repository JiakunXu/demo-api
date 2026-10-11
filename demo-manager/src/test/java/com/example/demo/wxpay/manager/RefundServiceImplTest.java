package com.example.demo.wxpay.manager;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.QueryByOutRefundNoRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefundServiceImplTest {
    @Test
    void create1BuildsRequest() {
        String outTradeNo = "outTradeNo-value";
        String outRefundNo = "outRefundNo-value";
        String reason = "reason-value";
        String notifyUrl = "notifyUrl-value";
        Long refund = 120L;
        Long total = 120L;
        com.wechat.pay.java.service.refund.RefundService client = mock(com.wechat.pay.java.service.refund.RefundService.class);
        RefundServiceImpl target = new RefundServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "merchantConfig", config);
        Refund response = new Refund();
        when(client.create(any(CreateRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.refund.RefundService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.create(outTradeNo, outRefundNo, reason, notifyUrl, refund, total)).isSameAs(response);
            ArgumentCaptor<CreateRequest> requestCaptor = ArgumentCaptor.forClass(CreateRequest.class);
            verify(client).create(requestCaptor.capture());
            CreateRequest request = requestCaptor.getValue();
            assertThat(request.getOutTradeNo()).isEqualTo(outTradeNo);
            assertThat(request.getOutRefundNo()).isEqualTo(outRefundNo);
            assertThat(request.getReason()).isEqualTo(reason);
            assertThat(request.getNotifyUrl()).isEqualTo(notifyUrl);
            assertThat(request.getAmount().getRefund()).isEqualTo(refund);
            assertThat(request.getAmount().getTotal()).isEqualTo(total);
            assertThat(request.getAmount().getCurrency()).isEqualTo("CNY");
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void create1ConvertsSdkErrors(String kind) {
        String outTradeNo = "outTradeNo-value";
        String outRefundNo = "outRefundNo-value";
        String reason = "reason-value";
        String notifyUrl = "notifyUrl-value";
        Long refund = 120L;
        Long total = 120L;
        com.wechat.pay.java.service.refund.RefundService client = mock(com.wechat.pay.java.service.refund.RefundService.class);
        RefundServiceImpl target = new RefundServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.create(any(CreateRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.refund.RefundService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.create(outTradeNo, outRefundNo, reason, notifyUrl, refund, total)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).create(any(CreateRequest.class));
        }
    }

    @Test
    void create2BuildsRequest() {
        String subMchid = "subMchid-value";
        String outTradeNo = "outTradeNo-value";
        String outRefundNo = "outRefundNo-value";
        String reason = "reason-value";
        String notifyUrl = "notifyUrl-value";
        Long refund = 120L;
        Long total = 120L;
        com.wechat.pay.java.service.refund.RefundService client = mock(com.wechat.pay.java.service.refund.RefundService.class);
        RefundServiceImpl target = new RefundServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "partnerConfig", config);
        Refund response = new Refund();
        when(client.create(any(CreateRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.refund.RefundService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.create(subMchid, outTradeNo, outRefundNo, reason, notifyUrl, refund, total)).isSameAs(response);
            ArgumentCaptor<CreateRequest> requestCaptor = ArgumentCaptor.forClass(CreateRequest.class);
            verify(client).create(requestCaptor.capture());
            CreateRequest request = requestCaptor.getValue();
            assertThat(request.getSubMchid()).isEqualTo(subMchid);
            assertThat(request.getOutTradeNo()).isEqualTo(outTradeNo);
            assertThat(request.getOutRefundNo()).isEqualTo(outRefundNo);
            assertThat(request.getReason()).isEqualTo(reason);
            assertThat(request.getNotifyUrl()).isEqualTo(notifyUrl);
            assertThat(request.getAmount().getRefund()).isEqualTo(refund);
            assertThat(request.getAmount().getTotal()).isEqualTo(total);
            assertThat(request.getAmount().getCurrency()).isEqualTo("CNY");
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void create2ConvertsSdkErrors(String kind) {
        String subMchid = "subMchid-value";
        String outTradeNo = "outTradeNo-value";
        String outRefundNo = "outRefundNo-value";
        String reason = "reason-value";
        String notifyUrl = "notifyUrl-value";
        Long refund = 120L;
        Long total = 120L;
        com.wechat.pay.java.service.refund.RefundService client = mock(com.wechat.pay.java.service.refund.RefundService.class);
        RefundServiceImpl target = new RefundServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.create(any(CreateRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.refund.RefundService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.create(subMchid, outTradeNo, outRefundNo, reason, notifyUrl, refund, total)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).create(any(CreateRequest.class));
        }
    }

    @Test
    void queryByOutRefundNo3BuildsRequest() {
        String outRefundNo = "outRefundNo-value";
        com.wechat.pay.java.service.refund.RefundService client = mock(com.wechat.pay.java.service.refund.RefundService.class);
        RefundServiceImpl target = new RefundServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "merchantConfig", config);
        Refund response = new Refund();
        when(client.queryByOutRefundNo(any(QueryByOutRefundNoRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.refund.RefundService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.queryByOutRefundNo(outRefundNo)).isSameAs(response);
            ArgumentCaptor<QueryByOutRefundNoRequest> requestCaptor = ArgumentCaptor.forClass(QueryByOutRefundNoRequest.class);
            verify(client).queryByOutRefundNo(requestCaptor.capture());
            QueryByOutRefundNoRequest request = requestCaptor.getValue();
            assertThat(request.getOutRefundNo()).isEqualTo(outRefundNo);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void queryByOutRefundNo3ConvertsSdkErrors(String kind) {
        String outRefundNo = "outRefundNo-value";
        com.wechat.pay.java.service.refund.RefundService client = mock(com.wechat.pay.java.service.refund.RefundService.class);
        RefundServiceImpl target = new RefundServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.queryByOutRefundNo(any(QueryByOutRefundNoRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.refund.RefundService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.queryByOutRefundNo(outRefundNo)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).queryByOutRefundNo(any(QueryByOutRefundNoRequest.class));
        }
    }

    @Test
    void queryByOutRefundNo4BuildsRequest() {
        String subMchid = "subMchid-value";
        String outRefundNo = "outRefundNo-value";
        com.wechat.pay.java.service.refund.RefundService client = mock(com.wechat.pay.java.service.refund.RefundService.class);
        RefundServiceImpl target = new RefundServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "partnerConfig", config);
        Refund response = new Refund();
        when(client.queryByOutRefundNo(any(QueryByOutRefundNoRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.refund.RefundService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.queryByOutRefundNo(subMchid, outRefundNo)).isSameAs(response);
            ArgumentCaptor<QueryByOutRefundNoRequest> requestCaptor = ArgumentCaptor.forClass(QueryByOutRefundNoRequest.class);
            verify(client).queryByOutRefundNo(requestCaptor.capture());
            QueryByOutRefundNoRequest request = requestCaptor.getValue();
            assertThat(request.getSubMchid()).isEqualTo(subMchid);
            assertThat(request.getOutRefundNo()).isEqualTo(outRefundNo);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void queryByOutRefundNo4ConvertsSdkErrors(String kind) {
        String subMchid = "subMchid-value";
        String outRefundNo = "outRefundNo-value";
        com.wechat.pay.java.service.refund.RefundService client = mock(com.wechat.pay.java.service.refund.RefundService.class);
        RefundServiceImpl target = new RefundServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.queryByOutRefundNo(any(QueryByOutRefundNoRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.refund.RefundService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.queryByOutRefundNo(subMchid, outRefundNo)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).queryByOutRefundNo(any(QueryByOutRefundNoRequest.class));
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
