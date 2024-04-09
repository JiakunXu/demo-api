package com.example.demo.wxpay.manager;

import com.example.demo.wxpay.api.TransferBatchService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.transferbatch.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferBatchServiceImpl implements TransferBatchService {

    private static final Logger logger = LoggerFactory.getLogger(TransferBatchServiceImpl.class);

    @Autowired(required = false)
    private Config              partnerConfig;

    @Override
    public TransferBatchEntity getTransferBatchByOutNo(String outBatchNo) {
        com.wechat.pay.java.service.transferbatch.TransferBatchService service = new com.wechat.pay.java.service.transferbatch.TransferBatchService.Builder()
            .config(partnerConfig).build();

        GetTransferBatchByOutNoRequest request = new GetTransferBatchByOutNoRequest();
        request.setOutBatchNo(outBatchNo);
        request.setNeedQueryDetail(Boolean.FALSE);

        try {
            return service.getTransferBatchByOutNo(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public InitiateBatchTransferResponse initiateBatchTransfer(String appid, String outBatchNo,
                                                               String batchName, String batchRemark,
                                                               Long totalAmount, Integer totalNum,
                                                               String transferSceneId,
                                                               List<TransferDetailInput> transferDetailList) {
        com.wechat.pay.java.service.transferbatch.TransferBatchService service = new com.wechat.pay.java.service.transferbatch.TransferBatchService.Builder()
            .config(partnerConfig).build();

        InitiateBatchTransferRequest request = new InitiateBatchTransferRequest();
        request.setAppid(appid);
        request.setOutBatchNo(outBatchNo);
        request.setBatchName(batchName);
        request.setBatchRemark(batchRemark);
        request.setTotalAmount(totalAmount);
        request.setTotalNum(totalNum);
        request.setTransferSceneId(transferSceneId);
        request.setTransferDetailList(transferDetailList);

        try {
            return service.initiateBatchTransfer(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public TransferDetailEntity getTransferDetailByOutNo(String outBatchNo, String outDetailNo) {
        com.wechat.pay.java.service.transferbatch.TransferBatchService service = new com.wechat.pay.java.service.transferbatch.TransferBatchService.Builder()
            .config(partnerConfig).build();

        GetTransferDetailByOutNoRequest request = new GetTransferDetailByOutNoRequest();
        request.setOutBatchNo(outBatchNo);
        request.setOutDetailNo(outDetailNo);

        try {
            return service.getTransferDetailByOutNo(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
