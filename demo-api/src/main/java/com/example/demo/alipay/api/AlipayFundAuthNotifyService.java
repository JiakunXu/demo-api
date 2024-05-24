package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.fund.AlipayFundAuthNotify;

import java.util.Map;

public interface AlipayFundAuthNotifyService {

    AlipayFundAuthNotify getAlipayFundAuthNotify(Map<String, String> parameters);

    AlipayFundAuthNotify insertAlipayFundAuthNotify(AlipayFundAuthNotify alipayFundAuthNotify);

}
