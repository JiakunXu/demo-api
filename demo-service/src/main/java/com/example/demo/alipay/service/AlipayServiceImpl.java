package com.example.demo.alipay.service;

import com.example.demo.alipay.api.AlipayService;
import com.example.demo.alipay.api.AlipayTradeWapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayTradeWapService alipayTradeWapService;

    @Override
    public String build(String appAuthToken, String subject, String outTradeNo,
                        String totalAmount) {
        return alipayTradeWapService.pay(appAuthToken, subject, outTradeNo, totalAmount, null,
            null);
    }

}
