package com.example.demo.alipay.api;

import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;

public interface FactoryPaymentPageService {

    AlipayTradePagePayResponse pay(String appAuthToken, String subject, String outTradeNo,
                                   String totalAmount, String returnUrl);

}
