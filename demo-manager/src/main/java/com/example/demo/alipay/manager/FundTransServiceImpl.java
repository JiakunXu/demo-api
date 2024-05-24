package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.example.demo.alipay.api.FundTransService;
import com.example.demo.alipay.api.bo.fund.FundTransUniTransferResponse;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class FundTransServiceImpl implements FundTransService {

    @Override
    public FundTransUniTransferResponse transfer(String outBizNo, BigDecimal transAmount,
                                                 String orderTitle, String userId) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("out_biz_no", outBizNo);
        bizParams.put("trans_amount", transAmount);
        bizParams.put("biz_scene", "DIRECT_TRANSFER");
        bizParams.put("product_code", "TRANS_ACCOUNT_NO_PWD");
        bizParams.put("order_title", orderTitle);
        bizParams.put("payee_info", new PayeeInfo(userId, "ALIPAY_USER_ID"));

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("alipay.fund.trans.uni.transfer", null, bizParams);
            if (ResponseChecker.success(response)) {
                return JSON.parseObject(
                    JSON.parseObject(response.getHttpBody(), JSONObject.class)
                        .getString("alipay_fund_trans_uni_transfer_response"),
                    FundTransUniTransferResponse.class);
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static class PayeeInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = -1055560037545902696L;

        private String            identity;

        @JSONField(name = "identity_type")
        private String            identityType;

        public PayeeInfo(String identity, String identityType) {
            this.identity = identity;
            this.identityType = identityType;
        }

    }

}
