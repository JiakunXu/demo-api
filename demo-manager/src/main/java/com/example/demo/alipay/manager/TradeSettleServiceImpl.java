package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.example.demo.alipay.api.TradeSettleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TradeSettleServiceImpl implements TradeSettleService {

    @Override
    public String confirm(String outRequestNo, String tradeNo) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("out_request_no", outRequestNo);
        bizParams.put("trade_no", tradeNo);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("alipay.trade.create", null, bizParams);
            if (ResponseChecker.success(response)) {
                return response.getHttpBody();
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
