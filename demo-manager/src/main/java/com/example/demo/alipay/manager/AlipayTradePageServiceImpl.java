package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.example.demo.alipay.api.AlipayTradePageService;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

@Service
public class AlipayTradePageServiceImpl implements AlipayTradePageService {

    @Override
    public String pay(String appAuthToken, String subject, String outTradeNo, String totalAmount,
                      String returnUrl) {
        try {
            AlipayTradePagePayResponse response = Factory.Payment.Page().agent(appAuthToken)
                .pay(URLEncoder.encode(subject, "UTF-8"), outTradeNo, totalAmount, returnUrl);
            if (ResponseChecker.success(response)) {
                return response.getBody();
            } else {
                throw new RuntimeException("调用失败");
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
