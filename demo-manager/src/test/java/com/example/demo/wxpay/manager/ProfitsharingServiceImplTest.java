package com.example.demo.wxpay.manager;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.profitsharing.model.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfitsharingServiceImplTest {
    @Test
    void createOrder1BuildsRequest() {
        String appid = "appid-value";
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        List<CreateOrderReceiver> receivers = java.util.List.of(new CreateOrderReceiver());
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "merchantConfig", config);
        OrdersEntity response = new OrdersEntity();
        when(client.createOrder(any(CreateOrderRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.createOrder(appid, transactionId, outOrderNo, receivers)).isSameAs(response);
            ArgumentCaptor<CreateOrderRequest> requestCaptor = ArgumentCaptor.forClass(CreateOrderRequest.class);
            verify(client).createOrder(requestCaptor.capture());
            CreateOrderRequest request = requestCaptor.getValue();
            assertThat(request.getAppid()).isEqualTo(appid);
            assertThat(request.getTransactionId()).isEqualTo(transactionId);
            assertThat(request.getOutOrderNo()).isEqualTo(outOrderNo);
            assertThat(request.getReceivers()).isEqualTo(receivers);
            assertThat(request.getUnfreezeUnsplit()).isEqualTo(Boolean.TRUE);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void createOrder1ConvertsSdkErrors(String kind) {
        String appid = "appid-value";
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        List<CreateOrderReceiver> receivers = java.util.List.of(new CreateOrderReceiver());
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.createOrder(any(CreateOrderRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.createOrder(appid, transactionId, outOrderNo, receivers)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).createOrder(any(CreateOrderRequest.class));
        }
    }

    @Test
    void createOrder2BuildsRequest() {
        String subMchid = "subMchid-value";
        String appid = "appid-value";
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        List<CreateOrderReceiver> receivers = java.util.List.of(new CreateOrderReceiver());
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "partnerConfig", config);
        OrdersEntity response = new OrdersEntity();
        when(client.createOrder(any(CreateOrderRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.createOrder(subMchid, appid, transactionId, outOrderNo, receivers)).isSameAs(response);
            ArgumentCaptor<CreateOrderRequest> requestCaptor = ArgumentCaptor.forClass(CreateOrderRequest.class);
            verify(client).createOrder(requestCaptor.capture());
            CreateOrderRequest request = requestCaptor.getValue();
            assertThat(request.getSubMchid()).isEqualTo(subMchid);
            assertThat(request.getAppid()).isEqualTo(appid);
            assertThat(request.getTransactionId()).isEqualTo(transactionId);
            assertThat(request.getOutOrderNo()).isEqualTo(outOrderNo);
            assertThat(request.getReceivers()).isEqualTo(receivers);
            assertThat(request.getUnfreezeUnsplit()).isEqualTo(Boolean.TRUE);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void createOrder2ConvertsSdkErrors(String kind) {
        String subMchid = "subMchid-value";
        String appid = "appid-value";
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        List<CreateOrderReceiver> receivers = java.util.List.of(new CreateOrderReceiver());
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.createOrder(any(CreateOrderRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.createOrder(subMchid, appid, transactionId, outOrderNo, receivers)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).createOrder(any(CreateOrderRequest.class));
        }
    }

    @Test
    void queryOrder3BuildsRequest() {
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "merchantConfig", config);
        OrdersEntity response = new OrdersEntity();
        when(client.queryOrder(any(QueryOrderRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.queryOrder(transactionId, outOrderNo)).isSameAs(response);
            ArgumentCaptor<QueryOrderRequest> requestCaptor = ArgumentCaptor.forClass(QueryOrderRequest.class);
            verify(client).queryOrder(requestCaptor.capture());
            QueryOrderRequest request = requestCaptor.getValue();
            assertThat(request.getTransactionId()).isEqualTo(transactionId);
            assertThat(request.getOutOrderNo()).isEqualTo(outOrderNo);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void queryOrder3ConvertsSdkErrors(String kind) {
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.queryOrder(any(QueryOrderRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.queryOrder(transactionId, outOrderNo)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).queryOrder(any(QueryOrderRequest.class));
        }
    }

    @Test
    void queryOrder4BuildsRequest() {
        String subMchid = "subMchid-value";
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "partnerConfig", config);
        OrdersEntity response = new OrdersEntity();
        when(client.queryOrder(any(QueryOrderRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.queryOrder(subMchid, transactionId, outOrderNo)).isSameAs(response);
            ArgumentCaptor<QueryOrderRequest> requestCaptor = ArgumentCaptor.forClass(QueryOrderRequest.class);
            verify(client).queryOrder(requestCaptor.capture());
            QueryOrderRequest request = requestCaptor.getValue();
            assertThat(request.getSubMchid()).isEqualTo(subMchid);
            assertThat(request.getTransactionId()).isEqualTo(transactionId);
            assertThat(request.getOutOrderNo()).isEqualTo(outOrderNo);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void queryOrder4ConvertsSdkErrors(String kind) {
        String subMchid = "subMchid-value";
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.queryOrder(any(QueryOrderRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.queryOrder(subMchid, transactionId, outOrderNo)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).queryOrder(any(QueryOrderRequest.class));
        }
    }

    @Test
    void unfreezeOrder5BuildsRequest() {
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        String description = "description-value";
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "merchantConfig", config);
        OrdersEntity response = new OrdersEntity();
        when(client.unfreezeOrder(any(UnfreezeOrderRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.unfreezeOrder(transactionId, outOrderNo, description)).isSameAs(response);
            ArgumentCaptor<UnfreezeOrderRequest> requestCaptor = ArgumentCaptor.forClass(UnfreezeOrderRequest.class);
            verify(client).unfreezeOrder(requestCaptor.capture());
            UnfreezeOrderRequest request = requestCaptor.getValue();
            assertThat(request.getTransactionId()).isEqualTo(transactionId);
            assertThat(request.getOutOrderNo()).isEqualTo(outOrderNo);
            assertThat(request.getDescription()).isEqualTo(description);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void unfreezeOrder5ConvertsSdkErrors(String kind) {
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        String description = "description-value";
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.unfreezeOrder(any(UnfreezeOrderRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.unfreezeOrder(transactionId, outOrderNo, description)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).unfreezeOrder(any(UnfreezeOrderRequest.class));
        }
    }

    @Test
    void unfreezeOrder6BuildsRequest() {
        String subMchid = "subMchid-value";
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        String description = "description-value";
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "partnerConfig", config);
        OrdersEntity response = new OrdersEntity();
        when(client.unfreezeOrder(any(UnfreezeOrderRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.unfreezeOrder(subMchid, transactionId, outOrderNo, description)).isSameAs(response);
            ArgumentCaptor<UnfreezeOrderRequest> requestCaptor = ArgumentCaptor.forClass(UnfreezeOrderRequest.class);
            verify(client).unfreezeOrder(requestCaptor.capture());
            UnfreezeOrderRequest request = requestCaptor.getValue();
            assertThat(request.getSubMchid()).isEqualTo(subMchid);
            assertThat(request.getTransactionId()).isEqualTo(transactionId);
            assertThat(request.getOutOrderNo()).isEqualTo(outOrderNo);
            assertThat(request.getDescription()).isEqualTo(description);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void unfreezeOrder6ConvertsSdkErrors(String kind) {
        String subMchid = "subMchid-value";
        String transactionId = "transactionId-value";
        String outOrderNo = "outOrderNo-value";
        String description = "description-value";
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.unfreezeOrder(any(UnfreezeOrderRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.unfreezeOrder(subMchid, transactionId, outOrderNo, description)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).unfreezeOrder(any(UnfreezeOrderRequest.class));
        }
    }

    @Test
    void addReceiver7BuildsRequest() {
        String appid = "appid-value";
        ReceiverType type = ReceiverType.values()[0];
        String account = "account-value";
        String name = "name-value";
        ReceiverRelationType relationType = ReceiverRelationType.values()[0];
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "merchantConfig", config);
        AddReceiverResponse response = new AddReceiverResponse();
        when(client.addReceiver(any(AddReceiverRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.addReceiver(appid, type, account, name, relationType)).isSameAs(response);
            ArgumentCaptor<AddReceiverRequest> requestCaptor = ArgumentCaptor.forClass(AddReceiverRequest.class);
            verify(client).addReceiver(requestCaptor.capture());
            AddReceiverRequest request = requestCaptor.getValue();
            assertThat(request.getAppid()).isEqualTo(appid);
            assertThat(request.getType()).isEqualTo(type);
            assertThat(request.getAccount()).isEqualTo(account);
            assertThat(request.getName()).isEqualTo(name);
            assertThat(request.getRelationType()).isEqualTo(relationType);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void addReceiver7ConvertsSdkErrors(String kind) {
        String appid = "appid-value";
        ReceiverType type = ReceiverType.values()[0];
        String account = "account-value";
        String name = "name-value";
        ReceiverRelationType relationType = ReceiverRelationType.values()[0];
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.addReceiver(any(AddReceiverRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.addReceiver(appid, type, account, name, relationType)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).addReceiver(any(AddReceiverRequest.class));
        }
    }

    @Test
    void addReceiver8BuildsRequest() {
        String subMchid = "subMchid-value";
        String appid = "appid-value";
        ReceiverType type = ReceiverType.values()[0];
        String account = "account-value";
        String name = "name-value";
        ReceiverRelationType relationType = ReceiverRelationType.values()[0];
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "partnerConfig", config);
        AddReceiverResponse response = new AddReceiverResponse();
        when(client.addReceiver(any(AddReceiverRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.addReceiver(subMchid, appid, type, account, name, relationType)).isSameAs(response);
            ArgumentCaptor<AddReceiverRequest> requestCaptor = ArgumentCaptor.forClass(AddReceiverRequest.class);
            verify(client).addReceiver(requestCaptor.capture());
            AddReceiverRequest request = requestCaptor.getValue();
            assertThat(request.getSubMchid()).isEqualTo(subMchid);
            assertThat(request.getAppid()).isEqualTo(appid);
            assertThat(request.getType()).isEqualTo(type);
            assertThat(request.getAccount()).isEqualTo(account);
            assertThat(request.getName()).isEqualTo(name);
            assertThat(request.getRelationType()).isEqualTo(relationType);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void addReceiver8ConvertsSdkErrors(String kind) {
        String subMchid = "subMchid-value";
        String appid = "appid-value";
        ReceiverType type = ReceiverType.values()[0];
        String account = "account-value";
        String name = "name-value";
        ReceiverRelationType relationType = ReceiverRelationType.values()[0];
        com.wechat.pay.java.service.profitsharing.ProfitsharingService client = mock(com.wechat.pay.java.service.profitsharing.ProfitsharingService.class);
        ProfitsharingServiceImpl target = new ProfitsharingServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.addReceiver(any(AddReceiverRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.addReceiver(subMchid, appid, type, account, name, relationType)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).addReceiver(any(AddReceiverRequest.class));
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
