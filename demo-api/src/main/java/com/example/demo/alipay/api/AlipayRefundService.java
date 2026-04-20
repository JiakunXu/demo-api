package com.example.demo.alipay.api;

import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.example.demo.alipay.api.bo.AlipayRefund;

import java.util.Map;

public interface AlipayRefundService {

    AlipayRefund getRefund(AlipayTradeRefundResponse response);

    AlipayRefund getRefund(AlipayTradeFastpayRefundQueryResponse response);

    AlipayRefund getRefund(String outTradeNo, String outRequestNo);

    AlipayRefund getRefund(Map<String, String> parameters);

    AlipayRefund insertRefund(AlipayRefund refund);

}
