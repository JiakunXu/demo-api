package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.example.demo.alipay.api.FactoryPaymentWapService;
import org.springframework.stereotype.Service;

@Service
public class FactoryPaymentWapServiceImpl implements FactoryPaymentWapService {

    @Override
    public AlipayTradeWapPayResponse pay(String appAuthToken, String subject, String outTradeNo,
                                         String totalAmount, String quitUrl, String returnUrl) {
        try {
            AlipayTradeWapPayResponse response = Factory.Payment.Wap().agent(appAuthToken)
                .pay(subject, outTradeNo, totalAmount, quitUrl, returnUrl);
            if (ResponseChecker.success(response)) {
                return response;
            } else {
                throw new RuntimeException("调用失败");
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
