package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.trade.TradeOrderPayResponse;

public interface TradeOrderService {

    TradeOrderPayResponse pay(String tradeNo);

}
