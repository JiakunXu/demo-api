package com.example.demo.alipay.service;

import com.example.demo.alipay.api.AlipayService;
import com.example.demo.alipay.api.TradeWapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private TradeWapService tradeWapService;

    @Override
    public String build(String appAuthToken, String subject, String outTradeNo,
                        String totalAmount) {
        return tradeWapService.pay(appAuthToken, subject, outTradeNo, totalAmount, null, null);
    }

}
