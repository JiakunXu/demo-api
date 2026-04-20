package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.AlipayTrade;

import java.util.Map;

public interface AlipayTradeService {

    AlipayTrade getTrade(Map<String, String> parameters);

    AlipayTrade getTrade(String appAuthToken, String outTradeNo);

    AlipayTrade insertTrade(AlipayTrade trade);

}
