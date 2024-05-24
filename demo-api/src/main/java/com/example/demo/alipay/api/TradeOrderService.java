package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.trade.AlipayTradeOrderPayResponse;

public interface TradeOrderService {

    AlipayTradeOrderPayResponse pay(String tradeNo);

}
