package com.example.demo.wxpay.api;

public interface WxpayService {

    String build(String spAppid, String openid, String subMchid, String description, String payNo,
                 int totalFee, String timeExpire, String attach);

    String build(String spAppid, String subMchid, String description, String payNo, int totalFee,
                 String timeExpire, String attach, String ip);

}
