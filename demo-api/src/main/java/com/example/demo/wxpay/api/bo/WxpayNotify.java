package com.example.demo.wxpay.api.bo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxpayNotify implements Serializable {

    private static final long serialVersionUID = -5946034537353065485L;

    /**
     * 通知id
     */
    private String            id;

    /**
     * 通知创建时间
     */
    private String            createTime;

    /**
     * 通知类型
     */
    private String            eventType;

    /**
     * 通知数据类型
     */
    private String            resourceType;

    /**
     * 回调摘要
     */
    private String            summary;

    /**
     * 服务商应用id
     */
    private String            spAppid;

    /**
     * 服务商户号
     */
    private String            spMchid;

    /**
     * 子商户应用id
     */
    private String            subAppid;

    /**
     * 子商户号
     */
    private String            subMchid;

    /**
     * 商户订单号
     */
    private String            outTradeNo;

    /**
     * 微信支付订单号
     */
    private String            transactionId;

    /**
     * 交易类型
     */
    private String            tradeType;

    /**
     * 交易状态
     */
    private String            tradeState;

    /**
     * 交易状态描述
     */
    private String            tradeStateDesc;

    /**
     * 付款银行
     */
    private String            bankType;

    /**
     * 附加数据
     */
    private String            attach;

    /**
     * 支付完成时间
     */
    private String            successTime;

    /**
     * 支付者
     */
    private String            payer;

    /**
     * 订单金额
     */
    private String            amount;

    /**
     * 场景信息
     */
    private String            sceneInfo;

    /**
     * 优惠功能
     */
    private String            promotionDetail;

}
