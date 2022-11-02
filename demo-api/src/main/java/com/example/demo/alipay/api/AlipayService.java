package com.example.demo.alipay.api;

public interface AlipayService {

    String build(String appAuthToken, String subject, String outTradeNo, String totalAmount);

}
