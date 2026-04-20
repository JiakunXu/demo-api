package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.*;
import com.example.demo.alipay.api.FactoryPaymentCommonService;
import org.springframework.stereotype.Service;

@Service
public class FactoryPaymentCommonServiceImpl implements FactoryPaymentCommonService {

    @Override
    public AlipayTradeCreateResponse create(String appAuthToken, String subject, String outTradeNo,
                                            String totalAmount, String buyerId) {
        try {
            AlipayTradeCreateResponse response = Factory.Payment.Common().agent(appAuthToken)
                .create(subject, outTradeNo, totalAmount, buyerId);
            if (ResponseChecker.success(response)) {
                return response;
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public AlipayTradeQueryResponse query(String appAuthToken, String outTradeNo) {
        try {
            AlipayTradeQueryResponse response = Factory.Payment.Common().agent(appAuthToken)
                .query(outTradeNo);
            if (ResponseChecker.success(response)) {
                return response;
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public AlipayTradeRefundResponse refund(String appAuthToken, String outTradeNo,
                                            String refundAmount) {
        try {
            AlipayTradeRefundResponse response = Factory.Payment.Common().agent(appAuthToken)
                .refund(outTradeNo, refundAmount);
            if (ResponseChecker.success(response)) {
                return response;
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public AlipayTradeCloseResponse close(String appAuthToken, String outTradeNo) {
        try {
            AlipayTradeCloseResponse response = Factory.Payment.Common().agent(appAuthToken)
                .close(outTradeNo);
            if (ResponseChecker.success(response)) {
                return response;
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public AlipayTradeCancelResponse cancel(String appAuthToken, String outTradeNo) {
        try {
            AlipayTradeCancelResponse response = Factory.Payment.Common().agent(appAuthToken)
                .cancel(outTradeNo);
            if (ResponseChecker.success(response)) {
                return response;
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
