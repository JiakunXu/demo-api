package com.example.demo.alipay.api.bo.fund;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
public class AlipayFundTransOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 3297727042898568022L;

    private BigInteger        id;

    /**
     * 商户端的唯一订单号
     */
    @JSONField(name = "out_biz_no")
    private String            outBizNo;

    /**
     * 销售产品码，商家和支付宝签约的产品码。
     * std_red_packet：现金红包；
     * trans_account_no_pwd：单笔无密转账
     */
    @JSONField(name = "product_code")
    private String            productCode;

    /**
     * 描述特定的业务场景，如果传递了out_biz_no则该字段为必传。可取的业务场景如下：
     * personal_pay：c2c现金红包-发红包；
     * personal_collection：c2c现金红包-领红包；
     * refund：c2c现金红包-红包退回；
     * direct_transfer：b2c现金红包、单笔无密转账
     */
    @JSONField(name = "biz_scene")
    private String            bizScene;

    /**
     * 请求来源的接口
     */
    @JSONField(name = "origin_interface")
    private String            originInterface;

    /**
     * 支付宝支付资金流水号，仅当转账成功才会返回该参数
     */
    @JSONField(name = "pay_fund_order_id")
    private String            payFundOrderId;

    /**
     * 支付宝转账单据号
     */
    @JSONField(name = "order_id")
    private String            orderId;

    /**
     * 转账单据状态。可能出现的状态如下：
     * success：转账成功；
     * wait_pay：等待支付；
     * closed：订单超时关闭
     *
     * alipay.fund.trans.app.pay涉及的状态： wait_pay success closed
     * alipay.fund.trans.uni.transfer、alipay.fund.trans.refund涉及的状态：success
     */
    private String            status;

    /**
     * 资金订单的操作类型，
     * create-创建；
     * finish- 订单处理已完结
     * close-超时关闭 ；
     */
    @JSONField(name = "action_type")
    private String            actionType;

    /**
     * 转账金额
     */
    @JSONField(name = "trans_amount")
    private String            transAmount;

    /**
     * 支付完成时间
     */
    @JSONField(name = "pay_date")
    private String            payDate;

    /**
     * 自动退款时间
     */
    @JSONField(name = "refund_date")
    private String            refundDate;

    /**
     * 无忧收场景下的受理单号
     */
    @JSONField(name = "entrust_order_id")
    private String            entrustOrderId;

    /**
     * 失败子单具体状态
     */
    @JSONField(name = "sub_order_status")
    private String            subOrderStatus;

    /**
     * 特殊场景提供，当子单出现异常导致主单失败或者退款时，会提供此字段，用于透出子单具体的错误场景
     */
    @JSONField(name = "sub_order_error_code")
    private String            subOrderErrorCode;

    /**
     * 特殊场景提供，当子单出现异常导致主单失败或者退款时，会提供此字段，用于透出子单具体的错误场景
     */
    @JSONField(name = "sub_order_fail_reason")
    private String            subOrderFailReason;

}
