package com.example.demo.wxpay.api.bo;

import java.io.Serializable;

public class Wxpay implements Serializable {

    private static final long serialVersionUID = -6151984177132162628L;

    /**
     * 小程序ID
     */
    private String            appId;

    /**
     * 时间戳
     */
    private String            timeStamp;

    /**
     * 随机字符串
     */
    private String            nonceStr;

    /**
     * 订单详情扩展字符串
     */
    private String            packageValue;

    /**
     * 签名方式
     */
    private String            signType;

    /**
     * 签名
     */
    private String            paySign;

}
