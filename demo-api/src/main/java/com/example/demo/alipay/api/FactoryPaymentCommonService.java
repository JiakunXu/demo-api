package com.example.demo.alipay.api;

import com.alipay.easysdk.payment.common.models.*;

public interface FactoryPaymentCommonService {

    AlipayTradeCreateResponse create(String appAuthToken, String subject, String outTradeNo,
                                     String totalAmount, String buyerId);

    AlipayTradeQueryResponse query(String appAuthToken, String outTradeNo);

    AlipayTradeRefundResponse refund(String appAuthToken, String outTradeNo, String refundAmount);

    AlipayTradeCloseResponse close(String appAuthToken, String outTradeNo);

    AlipayTradeCancelResponse cancel(String appAuthToken, String outTradeNo);

}
