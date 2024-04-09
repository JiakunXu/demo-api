package com.example.demo.wxpay.api;

import com.example.demo.wxpay.api.bo.WxpayNotify;

public interface WxpayNotifyService {

    WxpayNotify getWxpayNotify(String mchid, String outTradeNo);

    WxpayNotify getWxpayNotify(String spMchid, String subMchid, String outTradeNo);

    WxpayNotify getWxpayNotifyV1(String serialNumber, String nonce, String timestamp,
                                 String signature, String body);

    WxpayNotify getWxpayNotifyV2(String serialNumber, String nonce, String timestamp,
                                 String signature, String body);

    WxpayNotify insertWxpayNotify(WxpayNotify wxpayNotify);

}
