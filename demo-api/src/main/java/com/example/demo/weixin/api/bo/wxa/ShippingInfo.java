package com.example.demo.weixin.api.bo.wxa;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class ShippingInfo implements Serializable {

    private static final long serialVersionUID = -174323130548221296L;

    @JSONField(name = "order_key")
    private OrderKey          orderKey;

    @JSONField(name = "logistics_type")
    private Integer           logisticsType;

    @JSONField(name = "delivery_mode")
    private Integer           deliveryMode;

    @JSONField(name = "is_all_delivered")
    private Boolean           allDelivered;

    @JSONField(name = "shipping_list")
    private List<Shipping>    shippingList;

    @JSONField(name = "upload_time")
    private String            uploadTime;

    private Payer             payer;

    public ShippingInfo() {
    }

    public ShippingInfo(OrderKey orderKey, Integer logisticsType, Integer deliveryMode,
                        List<Shipping> shippingList, String uploadTime, Payer payer) {
        this.orderKey = orderKey;
        this.logisticsType = logisticsType;
        this.deliveryMode = deliveryMode;
        this.shippingList = shippingList;
        this.uploadTime = uploadTime;
        this.payer = payer;
    }

    @Getter
    @Setter
    @ToString
    public static class OrderKey implements Serializable {

        private static final long serialVersionUID = 65873314513630993L;

        @JSONField(name = "order_number_type")
        private Integer           orderNumberType;

        @JSONField(name = "transaction_id")
        private String            transactionId;

        private String            mchid;

        @JSONField(name = "out_trade_no")
        private String            outTradeNo;

        public OrderKey() {
        }

        public OrderKey(Integer orderNumberType, String transactionId) {
            this.orderNumberType = orderNumberType;
            this.transactionId = transactionId;
        }

    }

    @Getter
    @Setter
    @ToString
    public static class Shipping implements Serializable {

        private static final long serialVersionUID = 2053632799119217743L;

        @JSONField(name = "tracking_no")
        private String            trackingNo;

        @JSONField(name = "express_company")
        private String            expressCompany;

        @JSONField(name = "item_desc")
        private String            itemDesc;

        private Contact           contact;

        public Shipping() {
        }

        public Shipping(String itemDesc) {
            this.itemDesc = itemDesc;
        }

        @Getter
        @Setter
        @ToString
        public static class Contact implements Serializable {

            private static final long serialVersionUID = -2873975479310722783L;

            @JSONField(name = "consignor_contact")
            private String            consignorContact;

            @JSONField(name = "receiver_contact")
            private String            receiverContact;

        }

    }

    @Getter
    @Setter
    @ToString
    public static class Payer implements Serializable {

        private static final long serialVersionUID = -1818500801874140742L;

        private String            openid;

        public Payer() {
        }

        public Payer(String openid) {
            this.openid = openid;
        }

    }

}
