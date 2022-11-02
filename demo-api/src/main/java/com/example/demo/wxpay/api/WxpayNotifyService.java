package com.example.demo.wxpay.api;

import com.example.demo.wxpay.api.bo.WxpayNotify;

public interface WxpayNotifyService {

    WxpayNotify getWxpayNotify(String serialNumber, String nonce, String timestamp,
                               String signature, String body);

    WxpayNotify getWxpayNotify(String subMchid, String outTradeNo);

    WxpayNotify insertWxpayNotify(WxpayNotify wxpayNotify);

}
