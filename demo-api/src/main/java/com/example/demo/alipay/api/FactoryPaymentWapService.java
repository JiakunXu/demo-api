package com.example.demo.alipay.api;

import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;

public interface FactoryPaymentWapService {

    AlipayTradeWapPayResponse pay(String appAuthToken, String subject, String outTradeNo,
                                  String totalAmount, String quitUrl, String returnUrl);

}
