package com.example.demo.wxpay.api.bo;

import java.io.Serializable;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Transaction implements Serializable {

    private static final long serialVersionUID = -3810572764622456769L;

    /**
     * 服务商应用ID
     */
    @JSONField(name = "sp_appid")
    private String            spAppid;

    /**
     * 服务商户号
     */
    @JSONField(name = "sp_mchid")
    private String            spMchid;

    /**
     * 子商户应用ID
     */
    @JSONField(name = "sub_appid")
    private String            subAppid;

    /**
     * 子商户号
     */
    @JSONField(name = "sub_mchid")
    private String            subMchid;

    /**
     * 商品描述
     */
    private String            description;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String            outTradeNo;

    /**
     * 交易结束时间
     */
    @JSONField(name = "time_expire")
    private String            timeExpire;

    /**
     * 附加数据
     */
    private String            attach;

    /**
     * 通知地址
     */
    @JSONField(name = "notify_url")
    private String            notifyUrl;

    /**
     * 订单优惠标记
     */
    @JSONField(name = "goods_tag")
    private String            goodsTag;

    /**
     * 电子发票入口开放标识
     */
    @JSONField(name = "support_fapiao")
    private Boolean           supportFapiao;

    /**
     * 结算信息
     */
    @JSONField(name = "settle_info")
    private SettleInfo        settleInfo;

    /**
     * 订单金额
     */
    private Amount            amount;

    /**
     * 支付者
     */
    private Payer             payer;

    /**
     * 场景信息
     */
    @JSONField(name = "scene_info")
    private SceneInfo         sceneInfo;

    @Getter
    @Setter
    public static class SettleInfo implements Serializable {

        private static final long serialVersionUID = -1280454011907615357L;

        /**
         * 是否指定分账
         */
        @JSONField(name = "profit_sharing")
        private Boolean           profitSharing;

    }

    @Getter
    @Setter
    public static class Amount implements Serializable {

        private static final long serialVersionUID = 7579543742315364067L;

        /**
         * 总金额
         */
        private Integer           total;

        /**
         * 货币类型
         */
        private String            currency;

        public Amount() {

        }

        public Amount(Integer total) {
            this.total = total;
        }

    }

    @Getter
    @Setter
    public static class Payer implements Serializable {

        private static final long serialVersionUID = -3440636786499499052L;

        /**
         * 用户服务标识
         */
        @JSONField(name = "sp_openid")
        private String            spOpenid;

        /**
         * 用户子标识
         */
        @JSONField(name = "sub_openid")
        private String            subOpenid;

        public Payer() {

        }

        public Payer(String spOpenid) {
            this.spOpenid = spOpenid;
        }

    }

    @Getter
    @Setter
    public static class SceneInfo implements Serializable {

        private static final long serialVersionUID = -2738019262891167287L;

        /**
         * 用户终端IP
         */
        @JSONField(name = "payer_client_ip")
        private String            payerClientIp;

        /**
         * H5场景信息
         */
        @JSONField(name = "h5_info")
        private H5Info            h5Info;

        @Getter
        @Setter
        public static class H5Info implements Serializable {

            private static final long serialVersionUID = 3174661022600509072L;

            /**
             * 场景类型
             */
            private String            type;

            public H5Info() {
            }

            public H5Info(String type) {
                this.type = type;
            }

        }

    }

}
