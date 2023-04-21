package com.example.demo.alipay.api;

public interface AlipayTradeService {

    String create(String appAuthToken, String subject, String outTradeNo, String totalAmount,
                  String buyerId);

    String query(String appAuthToken, String outTradeNo);

    String refund(String appAuthToken, String outTradeNo, String refundAmount);

    String close(String appAuthToken, String outTradeNo);

    String cancel(String appAuthToken, String outTradeNo);

}
