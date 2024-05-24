package com.example.demo.alipay.api.bo.fund;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class FundTransUniTransferResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5615278100081362183L;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_biz_no")
    private String            outBizNo;

    /**
     * 支付宝转账订单号
     */
    @JSONField(name = "order_id")
    private String            orderId;

    /**
     * 支付宝支付资金流水号
     */
    @JSONField(name = "pay_fund_order_id")
    private String            payFundOrderId;

    /**
     * 转账单据状态。 SUCCESS（该笔转账交易成功）：成功； FAIL：失败（具体失败原因请参见error_code以及fail_reason返回值）；
     */
    private String            status;

    /**
     * 订单支付时间，格式为yyyy-MM-dd HH:mm:ss
     */
    @JSONField(name = "trans_date")
    private String            transDate;

}
