package com.example.demo.wxpay.api;

import com.example.demo.wxpay.api.bo.TransferBatch;
import com.example.demo.wxpay.api.bo.TransferDetail;

public interface TransferBatchService {

    String HTTPS_POST_URL = "https://api.mch.weixin.qq.com/v3/transfer/batches";

    String HTTPS_GET0_URL = "https://api.mch.weixin.qq.com/v3/transfer/batches/batch-id/{batch_id}";

    String HTTPS_GET1_URL = "https://api.mch.weixin.qq.com/v3/transfer/batches/out-batch-no/{out_batch_no}";

    String HTTPS_GET2_URL = "https://api.mch.weixin.qq.com/v3/transfer/batches/batch-id/{batch_id}/details/detail-id/{detail_id}";

    String HTTPS_GET3_URL = "https://api.mch.weixin.qq.com/v3/transfer/batches/out-batch-no/{out_batch_no}/details/out-detail-no/{out_detail_no}";

    /**
     * 发起商家转账
     *
     * @param transferBatch
     * @return
     */
    TransferBatch initiateBatchTransfer(TransferBatch transferBatch);

    /**
     * 通过微信批次单号查询批次单
     *
     * @param batchId
     * @return
     */
    TransferBatch getTransferBatchByNo(String batchId);

    /**
     * 通过商家批次单号查询批次单
     *
     * @param outBatchNo
     * @return
     */
    TransferBatch getTransferBatchByOutNo(String outBatchNo);

    /**
     * 通过微信明细单号查询明细单
     *
     * @param batchId
     * @param detailId
     * @return
     */
    TransferDetail getTransferDetailByNo(String batchId, String detailId);

    /**
     * 通过商家明细单号查询明细单
     *
     * @param outBatchNo
     * @param outDetailNo
     * @return
     */
    TransferDetail getTransferDetailByOutNo(String outBatchNo, String outDetailNo);

}
