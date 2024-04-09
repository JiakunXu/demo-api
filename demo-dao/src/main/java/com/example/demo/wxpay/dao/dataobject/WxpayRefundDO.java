package com.example.demo.wxpay.dao.dataobject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class WxpayRefundDO implements Serializable {

    private static final long serialVersionUID = 3624836700192458218L;

    /**
     * 微信支付退款号
     */
    private String            refundId;

    /**
     * 商户退款单号
     */
    private String            outRefundNo;

    /**
     * 微信支付订单号
     */
    private String            transactionId;

    /**
     * 商户订单号
     */
    private String            outTradeNo;

    /**
     * 退款渠道
     */
    private String            channel;

    /**
     * 退款入账账户
     */
    private String            userReceivedAccount;

    /**
     * 退款成功时间
     */
    private String            successTime;

    /**
     * 退款创建时间
     */
    private String            createTime;

    /**
     * 退款状态
     */
    private String            status;

    /**
     * 资金账户
     */
    private String            fundsAccount;

    /**
     * 金额信息
     */
    private String            amount;

    /**
     * 优惠退款信息
     */
    private String            promotionDetail;

}
