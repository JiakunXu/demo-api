package com.example.demo.wxpay.api;

import java.util.List;

import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferResponse;
import com.wechat.pay.java.service.transferbatch.model.TransferBatchEntity;
import com.wechat.pay.java.service.transferbatch.model.TransferDetailEntity;
import com.wechat.pay.java.service.transferbatch.model.TransferDetailInput;

public interface TransferBatchService {

    TransferBatchEntity getTransferBatchByOutNo(String outBatchNo);

    InitiateBatchTransferResponse initiateBatchTransfer(String appid, String outBatchNo,
                                                        String batchName, String batchRemark,
                                                        Long totalAmount, Integer totalNum,
                                                        String transferSceneId,
                                                        List<TransferDetailInput> transferDetailList);

    TransferDetailEntity getTransferDetailByOutNo(String outBatchNo, String outDetailNo);

}
