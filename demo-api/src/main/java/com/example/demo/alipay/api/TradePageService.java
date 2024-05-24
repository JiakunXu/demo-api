package com.example.demo.alipay.api;

public interface TradePageService {

    String pay(String appAuthToken, String subject, String outTradeNo, String totalAmount,
               String returnUrl);

}
