package com.example.demo.wxpay.api;

import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.model.Transaction;

public interface JsapiService {

    PrepayWithRequestPaymentResponse prepayWithRequestPayment(String appid, String mchid,
                                                              String description, String outTradeNo,
                                                              String timeExpire, String attach,
                                                              String notifyUrl, Integer total,
                                                              String spOpenid);

    void closeOrder(String mchid, String outTradeNo);

    Transaction queryOrderByOutTradeNo(String mchid, String outTradeNo);

}
