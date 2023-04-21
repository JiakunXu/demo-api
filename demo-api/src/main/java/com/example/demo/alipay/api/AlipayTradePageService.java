package com.example.demo.alipay.api;

public interface AlipayTradePageService {

    String pay(String appAuthToken, String subject, String outTradeNo, String totalAmount,
               String returnUrl);

}
