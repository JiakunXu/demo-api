package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.example.demo.alipay.api.AlipayTradeService;
import org.springframework.stereotype.Service;

@Service
public class AlipayTradeServiceImpl implements AlipayTradeService {

    @Override
    public String query(String appAuthToken, String outTradeNo) {
        try {
            AlipayTradeQueryResponse response = Factory.Payment.Common().agent(appAuthToken)
                .query(outTradeNo);
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
