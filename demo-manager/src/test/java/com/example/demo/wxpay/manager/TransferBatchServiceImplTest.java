package com.example.demo.wxpay.manager;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.transferbatch.model.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferBatchServiceImplTest {
    @Test
    void getTransferBatchByOutNo1BuildsRequest() {
        String outBatchNo = "outBatchNo-value";
        com.wechat.pay.java.service.transferbatch.TransferBatchService client = mock(com.wechat.pay.java.service.transferbatch.TransferBatchService.class);
        TransferBatchServiceImpl target = new TransferBatchServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "partnerConfig", config);
        TransferBatchEntity response = new TransferBatchEntity();
        when(client.getTransferBatchByOutNo(any(GetTransferBatchByOutNoRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.transferbatch.TransferBatchService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.getTransferBatchByOutNo(outBatchNo)).isSameAs(response);
            ArgumentCaptor<GetTransferBatchByOutNoRequest> requestCaptor = ArgumentCaptor.forClass(GetTransferBatchByOutNoRequest.class);
            verify(client).getTransferBatchByOutNo(requestCaptor.capture());
            GetTransferBatchByOutNoRequest request = requestCaptor.getValue();
            assertThat(request.getOutBatchNo()).isEqualTo(outBatchNo);
            assertThat(request.getNeedQueryDetail()).isEqualTo(Boolean.FALSE);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void getTransferBatchByOutNo1ConvertsSdkErrors(String kind) {
        String outBatchNo = "outBatchNo-value";
        com.wechat.pay.java.service.transferbatch.TransferBatchService client = mock(com.wechat.pay.java.service.transferbatch.TransferBatchService.class);
        TransferBatchServiceImpl target = new TransferBatchServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.getTransferBatchByOutNo(any(GetTransferBatchByOutNoRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.transferbatch.TransferBatchService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.getTransferBatchByOutNo(outBatchNo)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).getTransferBatchByOutNo(any(GetTransferBatchByOutNoRequest.class));
        }
    }

    @Test
    void initiateBatchTransfer2BuildsRequest() {
        String appid = "appid-value";
        String outBatchNo = "outBatchNo-value";
        String batchName = "batchName-value";
        String batchRemark = "batchRemark-value";
        Long totalAmount = 120L;
        Integer totalNum = 120;
        String transferSceneId = "transferSceneId-value";
        List<TransferDetailInput> transferDetailList = java.util.List.of(new TransferDetailInput());
        com.wechat.pay.java.service.transferbatch.TransferBatchService client = mock(com.wechat.pay.java.service.transferbatch.TransferBatchService.class);
        TransferBatchServiceImpl target = new TransferBatchServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "partnerConfig", config);
        InitiateBatchTransferResponse response = new InitiateBatchTransferResponse();
        when(client.initiateBatchTransfer(any(InitiateBatchTransferRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.transferbatch.TransferBatchService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.initiateBatchTransfer(appid, outBatchNo, batchName, batchRemark, totalAmount, totalNum, transferSceneId, transferDetailList)).isSameAs(response);
            ArgumentCaptor<InitiateBatchTransferRequest> requestCaptor = ArgumentCaptor.forClass(InitiateBatchTransferRequest.class);
            verify(client).initiateBatchTransfer(requestCaptor.capture());
            InitiateBatchTransferRequest request = requestCaptor.getValue();
            assertThat(request.getAppid()).isEqualTo(appid);
            assertThat(request.getOutBatchNo()).isEqualTo(outBatchNo);
            assertThat(request.getBatchName()).isEqualTo(batchName);
            assertThat(request.getBatchRemark()).isEqualTo(batchRemark);
            assertThat(request.getTotalAmount()).isEqualTo(totalAmount);
            assertThat(request.getTotalNum()).isEqualTo(totalNum);
            assertThat(request.getTransferSceneId()).isEqualTo(transferSceneId);
            assertThat(request.getTransferDetailList()).isEqualTo(transferDetailList);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void initiateBatchTransfer2ConvertsSdkErrors(String kind) {
        String appid = "appid-value";
        String outBatchNo = "outBatchNo-value";
        String batchName = "batchName-value";
        String batchRemark = "batchRemark-value";
        Long totalAmount = 120L;
        Integer totalNum = 120;
        String transferSceneId = "transferSceneId-value";
        List<TransferDetailInput> transferDetailList = java.util.List.of(new TransferDetailInput());
        com.wechat.pay.java.service.transferbatch.TransferBatchService client = mock(com.wechat.pay.java.service.transferbatch.TransferBatchService.class);
        TransferBatchServiceImpl target = new TransferBatchServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.initiateBatchTransfer(any(InitiateBatchTransferRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.transferbatch.TransferBatchService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.initiateBatchTransfer(appid, outBatchNo, batchName, batchRemark, totalAmount, totalNum, transferSceneId, transferDetailList)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).initiateBatchTransfer(any(InitiateBatchTransferRequest.class));
        }
    }

    @Test
    void getTransferDetailByOutNo3BuildsRequest() {
        String outBatchNo = "outBatchNo-value";
        String outDetailNo = "outDetailNo-value";
        com.wechat.pay.java.service.transferbatch.TransferBatchService client = mock(com.wechat.pay.java.service.transferbatch.TransferBatchService.class);
        TransferBatchServiceImpl target = new TransferBatchServiceImpl();
        Config config = mock(Config.class);
        ReflectionTestUtils.setField(target, "partnerConfig", config);
        TransferDetailEntity response = new TransferDetailEntity();
        when(client.getTransferDetailByOutNo(any(GetTransferDetailByOutNoRequest.class))).thenReturn(response);
        try (var builders = mockConstruction(com.wechat.pay.java.service.transferbatch.TransferBatchService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThat(target.getTransferDetailByOutNo(outBatchNo, outDetailNo)).isSameAs(response);
            ArgumentCaptor<GetTransferDetailByOutNoRequest> requestCaptor = ArgumentCaptor.forClass(GetTransferDetailByOutNoRequest.class);
            verify(client).getTransferDetailByOutNo(requestCaptor.capture());
            GetTransferDetailByOutNoRequest request = requestCaptor.getValue();
            assertThat(request.getOutBatchNo()).isEqualTo(outBatchNo);
            assertThat(request.getOutDetailNo()).isEqualTo(outDetailNo);
            verify(builders.constructed().getFirst()).config(config);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "http", "service", "malformed" })
    void getTransferDetailByOutNo3ConvertsSdkErrors(String kind) {
        String outBatchNo = "outBatchNo-value";
        String outDetailNo = "outDetailNo-value";
        com.wechat.pay.java.service.transferbatch.TransferBatchService client = mock(com.wechat.pay.java.service.transferbatch.TransferBatchService.class);
        TransferBatchServiceImpl target = new TransferBatchServiceImpl();
        RuntimeException failure = failure(kind);
        when(client.getTransferDetailByOutNo(any(GetTransferDetailByOutNoRequest.class))).thenThrow(failure);
        try (var builders = mockConstruction(com.wechat.pay.java.service.transferbatch.TransferBatchService.Builder.class, withSettings().defaultAnswer(RETURNS_SELF),
                (builder, context) -> when(builder.build()).thenReturn(client))) {
            assertThatThrownBy(() -> target.getTransferDetailByOutNo(outBatchNo, outDetailNo)).isInstanceOf(RuntimeException.class)
                .hasMessage(kind.equals("http") ? "发送HTTP请求失败" : "provider rejected");
            verify(client).getTransferDetailByOutNo(any(GetTransferDetailByOutNoRequest.class));
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
