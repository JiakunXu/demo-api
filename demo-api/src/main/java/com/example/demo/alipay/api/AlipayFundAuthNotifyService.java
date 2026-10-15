package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.fund.AlipayFundAuthNotify;

import java.util.Map;

public interface AlipayFundAuthNotifyService {

    AlipayFundAuthNotify getFundAuthNotify(Map<String, String> parameters);

    AlipayFundAuthNotify insertFundAuthNotify(AlipayFundAuthNotify notify);

}
