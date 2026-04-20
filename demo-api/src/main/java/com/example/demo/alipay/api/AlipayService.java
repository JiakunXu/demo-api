package com.example.demo.alipay.api;

import com.alipay.easysdk.payment.common.models.AlipayTradeCloseResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;

public interface AlipayService {

    String pay(String appAuthToken, String subject, String outTradeNo, String totalAmount);

    AlipayTradeQueryResponse query(String outTradeNo);

    AlipayTradeCloseResponse close(String outTradeNo);

    AlipayTradeRefundResponse refund(String outTradeNo, String outRequestNo, String refundAmount);

    AlipayTradeFastpayRefundQueryResponse queryRefund(String outTradeNo, String outRequestNo);

}
