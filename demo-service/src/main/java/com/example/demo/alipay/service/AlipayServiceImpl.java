package com.example.demo.alipay.service;

import com.alipay.easysdk.payment.common.models.AlipayTradeCloseResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.example.demo.alipay.api.AlipayService;
import com.example.demo.alipay.api.TradeWapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private TradeWapService tradeWapService;

    @Override
    public String pay(String appAuthToken, String subject, String outTradeNo, String totalAmount) {
        return tradeWapService.pay(appAuthToken, subject, outTradeNo, totalAmount, null, null);
    }

    @Override
    public AlipayTradeQueryResponse query(String outTradeNo) {
        return null;
    }

    @Override
    public AlipayTradeCloseResponse close(String outTradeNo) {
        return null;
    }

    @Override
    public AlipayTradeRefundResponse refund(String outTradeNo, String outRequestNo,
                                            String refundAmount) {
        return null;
    }

    @Override
    public AlipayTradeFastpayRefundQueryResponse queryRefund(String outTradeNo,
                                                             String outRequestNo) {
        return null;
    }

}
