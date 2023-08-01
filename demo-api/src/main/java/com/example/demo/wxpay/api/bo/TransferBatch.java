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
     * 商户appid
     */
    private String               appid;

    /**
     * 商户号
     */
    private String               mchid;

    /**
     * 商家批次单号
     */
    @JSONField(name = "out_batch_no")
    private String               outBatchNo;

    /**
     * 微信批次单号
     */
    @JSONField(name = "batch_id")
    private String               batchId;

    /**
     * 批次状态
     */
    @JSONField(name = "batch_status")
    private String               batchStatus;

    /**
     * 批次类型
     */
    @JSONField(name = "batch_type")
    private String               batchType;

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
     * 批次关闭原因
     */
    @JSONField(name = "close_reason")
    private String               closeReason;

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
     * 批次创建时间
     */
    @JSONField(name = "create_time")
    private String               createTime;

    /**
     * 批次更新时间
     */
    @JSONField(name = "update_time")
    private String               updateTime;

    /**
     * 转账成功金额
     */
    @JSONField(name = "success_amount")
    private Integer              successAmount;

    /**
     * 转账成功笔数
     */
    @JSONField(name = "success_num")
    private Integer              successNum;

    /**
     * 转账失败金额
     */
    @JSONField(name = "fail_amount")
    private Integer              failAmount;

    /**
     * 转账失败笔数
     */
    @JSONField(name = "fail_num")
    private Integer              failNum;

    /**
     * 转账场景ID
     */
    @JSONField(name = "transfer_scene_id")
    private String               transferSceneId;

    /**
     * 转账明细列表
     */
    @JSONField(name = "transfer_detail_list")
    private List<TransferDetail> transferDetailList;

}
