package com.example.demo.wxpay.api;

import com.wechat.pay.java.service.partnerpayments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;

public interface PartnerJsapiService {

    PrepayWithRequestPaymentResponse prepayWithRequestPayment(String spAppid, String spMchid,
                                                              String subMchid, String description,
                                                              String outTradeNo, String timeExpire,
                                                              String attach, String notifyUrl,
                                                              Integer total, String spOpenid);

    void closeOrder(String spMchid, String subMchid, String outTradeNo);

    Transaction queryOrderByOutTradeNo(String spMchid, String subMchid, String outTradeNo);

}
