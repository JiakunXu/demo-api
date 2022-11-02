package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.AlipayNotify;

import java.util.Map;

public interface AlipayNotifyService {

    AlipayNotify getAlipayNotify(Map<String, String> parameters);

    AlipayNotify getAlipayNotify(String appAuthToken, String outTradeNo);

    AlipayNotify insertAlipayNotify(AlipayNotify alipayNotify);

}
