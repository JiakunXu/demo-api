package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.fund.AlipayFundTransUniTransferResponse;

import java.math.BigDecimal;

public interface AlipayFundTransService {

    AlipayFundTransUniTransferResponse transfer(String outBizNo, BigDecimal transAmount,
                                                String orderTitle, String userId);

}
