package com.example.demo.wxpay.api.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class TransferBatch implements Serializable {

    private static final long    serialVersionUID = 564865569251972740L;

    /**
     * 直连商户的appid
     */
    private String               appid;

    /**
     * 商家批次单号
     */
    @JSONField(name = "out_batch_no")
    private String               outBatchNo;

    /**
     * 批次名称
     */
    @JSONField(name = "batch_name")
    private String               batchName;

    /**
     * 批次备注
     */
    @JSONField(name = "batch_remark")
    private String               batchRemark;

    /**
     * 转账总金额
     */
    @JSONField(name = "total_amount")
    private Integer              totalAmount;

    /**
     * 转账总笔数
     */
    @JSONField(name = "total_num")
    private Integer              totalNum;

    /**
     * 转账明细列表
     */
    @JSONField(name = "transfer_detail_list")
    private List<TransferDetail> transferDetailList;

    /**
     * 转账场景ID
     */
    @JSONField(name = "transfer_scene_id")
    private String               transferSceneId;

}
