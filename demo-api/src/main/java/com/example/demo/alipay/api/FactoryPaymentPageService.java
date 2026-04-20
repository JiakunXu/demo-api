package com.example.demo.alipay.api;

public interface FactoryPaymentPageService {

    String pay(String appAuthToken, String subject, String outTradeNo, String totalAmount,
               String returnUrl);

}
