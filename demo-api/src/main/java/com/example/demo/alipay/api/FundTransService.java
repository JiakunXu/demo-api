package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.fund.FundTransUniTransferResponse;

import java.math.BigDecimal;

public interface FundTransService {

    FundTransUniTransferResponse transfer(String outBizNo, BigDecimal transAmount,
                                          String orderTitle, String userId);

}
