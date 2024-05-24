package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericSDKResponse;
import com.example.demo.alipay.api.ZhimaCreditPayAfterUseCreditBizOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZhimaCreditPayAfterUseCreditBizOrderServiceImpl implements
                                                             ZhimaCreditPayAfterUseCreditBizOrderService {

    @Override
    public String order(String outOrderNo, String creditAgreementId, String categoryId,
                        BigDecimal orderAmount, String subject) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("out_order_no", outOrderNo);
        bizParams.put("credit_agreement_id", creditAgreementId);
        bizParams.put("category_id", categoryId);
        bizParams.put("order_amount", orderAmount);
        bizParams.put("amount_type", "ORDER_AMOUNT");
        bizParams.put("product_code", "CREDIT_PAY_AFTER_USE");
        bizParams.put("subject", subject);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("zhima.credit.payafteruse.creditbizorder.order", null, bizParams);
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
    public String create(String outOrderNo, BigDecimal orderAmount, String zmServiceId,
                         String categoryId, String subject) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("out_order_no", outOrderNo);
        bizParams.put("order_amount", orderAmount);
        bizParams.put("amount_type", "ORDER_AMOUNT");
        bizParams.put("zm_service_id", zmServiceId);
        bizParams.put("category_id", categoryId);
        bizParams.put("product_code", "CREDIT_PAY_AFTER_USE");
        bizParams.put("subject", subject);

        try {
            AlipayOpenApiGenericSDKResponse response = Factory.Util.Generic()
                .sdkExecute("zhima.credit.payafteruse.creditbizorder.create", null, bizParams);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String query(String creditBizOrderId, String outOrderNo) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("credit_biz_order_id", creditBizOrderId);
        bizParams.put("out_order_no", outOrderNo);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("zhima.credit.payafteruse.creditbizorder.query", null, bizParams);
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
    public String finish(String outRequestNo, String creditBizOrderId, String isFulfilled,
                         String remark) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("out_request_no", outRequestNo);
        bizParams.put("credit_biz_order_id", creditBizOrderId);
        bizParams.put("is_fulfilled", isFulfilled);
        bizParams.put("remark", remark);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("zhima.credit.payafteruse.creditbizorder.finish", null, bizParams);
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
