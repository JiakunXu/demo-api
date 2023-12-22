package com.example.demo.wxpay.api;

public interface WxpayService {

    String build(String spAppid, String openid, String subMchid, String description,
                 String outTradeNo, String timeExpire, String attach, int totalFee);

    String build(String spAppid, String subMchid, String description, String outTradeNo,
                 String timeExpire, String attach, int totalFee, String ip);

    String build(String spAppid, String subMchid, String description, String outTradeNo,
                 String timeExpire, String attach, int totalFee);

    void close(String subMchid, String outTradeNo);

}
