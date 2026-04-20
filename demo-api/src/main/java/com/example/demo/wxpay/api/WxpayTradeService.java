package com.example.demo.wxpay.api;

import com.example.demo.wxpay.api.bo.WxpayNotify;

public interface WxpayTradeService {

    WxpayNotify getTrade(String mchid, String outTradeNo);

    WxpayNotify getTrade(String spMchid, String subMchid, String outTradeNo);

    WxpayNotify getTradeV1(String serialNumber, String nonce, String timestamp, String signature,
                           String body);

    WxpayNotify getTradeV2(String serialNumber, String nonce, String timestamp, String signature,
                           String body);

    WxpayNotify insertTrade(WxpayNotify wxpayNotify);

}
