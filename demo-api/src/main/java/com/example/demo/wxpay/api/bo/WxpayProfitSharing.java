package com.example.demo.wxpay.api.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class WxpayProfitSharing implements Serializable {

    private static final long serialVersionUID = 2403560117182050673L;

    /**
     * 微信分账单号
     */
    private String            orderId;

    private String            mchid;

    private String            spMchid;

    /**
     * 子商户号
     */
    private String            subMchid;

    /**
     * 微信订单号
     */
    private String            transactionId;

    /**
     * 商户分账单号
     */
    private String            outOrderNo;

    /**
     * 分账单状态
     */
    private String            state;

    /**
     * 分账接收方列表
     */
    private String            receivers;

    private String            successTime;

    public enum State {
                       /**
                        * 分账单状态
                        */
                       PROCESSING("PROCESSING", "处理中"),

                       FINISHED("FINISHED", "分账完成");

        public final String value;

        public final String desc;

        State(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

}
