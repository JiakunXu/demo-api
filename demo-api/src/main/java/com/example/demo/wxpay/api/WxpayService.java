package com.example.demo.wxpay.api;

public interface WxpayService {

    String build(String description, String outTradeNo, String timeExpire, String attach,
                 int totalFee, String openid);

    String build(String subMchid, String description, String outTradeNo, String timeExpire,
                 String attach, int totalFee, String openid);

    void close(String outTradeNo);

    void close(String subMchid, String outTradeNo);

}
