package com.example.demo.alipay.api.bo.trade;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class AlipayTradeOrderPayResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 2077049729053890967L;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String            outTradeNo;

    /**
     * 支付宝交易号
     */
    @JSONField(name = "trade_no")
    private String            tradeNo;

    /**
     * 商户请求号
     */
    @JSONField(name = "out_request_no")
    private String            outRequestNo;

    /**
     * 订单总金额。元为单元，精确到2位小数。
     */
    @JSONField(name = "total_amount")
    private BigDecimal        totalAmount;

    /**
     * 交易支付时间
     */
    @JSONField(name = "gmt_payment")
    private Date              gmtPayment;

    /**
     * 异步支付模式。订单使用异步支付模式时才有值。
     */
    @JSONField(name = "async_payment_mode")
    private String            asyncPaymentMode;

}
