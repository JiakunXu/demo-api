package com.example.demo.alipay.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AlipayRefund implements Serializable {

    @Serial
    private static final long serialVersionUID = 7535712257940638603L;

    public String             tradeNo;

    public String             outTradeNo;

    public String             outRequestNo;

    public String             totalAmount;

    public String             refundAmount;

    public String             refundStatus;

    public String             gmtRefundPay;

    public String             sendBackFee;

    private String            fundChange;

    public enum RefundStatus {
                              /**
                               * 退款状态
                               */
                              REFUND_SUCCESS("REFUND_SUCCESS", "退款处理成功");

        public final String value;

        public final String desc;

        RefundStatus(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

}
