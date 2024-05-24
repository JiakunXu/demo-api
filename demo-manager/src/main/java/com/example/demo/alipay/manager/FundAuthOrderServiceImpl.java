package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.example.demo.alipay.api.FundAuthOrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class FundAuthOrderServiceImpl implements FundAuthOrderService {

    @Value("${alipay.fund.auth.notify.url}")
    private String notifyUrl;

    @Override
    public String freeze(String outOrderNo, String outRequestNo, String orderTitle,
                         BigDecimal amount, String timeoutExpress) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("out_order_no", outOrderNo);
        bizParams.put("out_request_no", outRequestNo);
        bizParams.put("order_title", orderTitle);
        bizParams.put("amount", amount);
        bizParams.put("product_code", "PRE_AUTH_ONLINE");
        bizParams.put("timeout_express", timeoutExpress);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic().asyncNotify(notifyUrl)
                .execute("alipay.fund.auth.order.app.freeze", null, bizParams);
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

    @Override
    public String unfreeze(String authNo, String outRequestNo, BigDecimal amount, String remark,
                           String extraParam) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("auth_no", authNo);
        bizParams.put("out_request_no", outRequestNo);
        bizParams.put("amount", amount);
        bizParams.put("remark", remark);
        bizParams.put("extra_param", extraParam);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic().asyncNotify(notifyUrl)
                .execute("alipay.fund.auth.order.unfreeze", null, bizParams);
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

    @Override
    public String query(String authNo, String outOrderNo, String operationId, String outRequestNo,
                        String operationType) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("auth_no", authNo);
        bizParams.put("out_order_no", outOrderNo);
        bizParams.put("operation_id", operationId);
        bizParams.put("out_request_no", outRequestNo);
        bizParams.put("operation_type", operationType);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("alipay.fund.auth.operation.detail.query", null, bizParams);
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

    @Override
    public String cancel(String authNo, String outOrderNo, String operationId, String outRequestNo,
                         String remark) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("auth_no", authNo);
        bizParams.put("out_order_no", outOrderNo);
        bizParams.put("operation_id", operationId);
        bizParams.put("out_request_no", outRequestNo);
        bizParams.put("remark", remark);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic().asyncNotify(notifyUrl)
                .execute("alipay.fund.auth.operation.detail.query", null, bizParams);
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
