package com.example.demo.alipay.api;

public interface AlipayTradeWapService {

    String pay(String appAuthToken, String subject, String outTradeNo, String totalAmount,
               String quitUrl, String returnUrl);

}
