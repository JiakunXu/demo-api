package com.example.demo.wxpay.api.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class WxpayRefund implements Serializable {

    private static final long serialVersionUID = -6858441116092822716L;

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

    public enum Status {
                        /**
                         * 退款状态
                         */
                        SUCCESS("SUCCESS", "退款成功"),

                        CLOSED("CLOSED", "退款关闭"),

                        PROCESSING("PROCESSING", "退款处理中"),

                        ABNORMAL("ABNORMAL", "退款异常");

        public final String value;

        public final String desc;

        Status(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

}
