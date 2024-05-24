package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.example.demo.alipay.api.TradeOrderService;
import com.example.demo.alipay.api.bo.trade.TradeOrderPayResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TradeOrderServiceImpl implements TradeOrderService {

    @Override
    public TradeOrderPayResponse pay(String tradeNo) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("trade_no", tradeNo);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("alipay.trade.order.pay", null, bizParams);
            if (ResponseChecker.success(response)) {
                return JSON.parseObject(JSON.parseObject(response.getHttpBody(), JSONObject.class)
                    .getString("alipay_trade_order_pay_response"),
                    TradeOrderPayResponse.class);
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
