package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericSDKResponse;
import com.example.demo.alipay.api.ZhimaCreditPayAfterUseCreditAgreementService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZhimaCreditPayAfterUseCreditAgreementServiceImpl implements
                                                              ZhimaCreditPayAfterUseCreditAgreementService {

    @Override
    public String sign(String outAgreementNo, String zmServiceId, String categoryId) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("out_agreement_no", outAgreementNo);
        bizParams.put("zm_service_id", zmServiceId);
        bizParams.put("category_id", categoryId);

        try {
            AlipayOpenApiGenericSDKResponse response = Factory.Util.Generic()
                .sdkExecute("zhima.credit.payafteruse.creditagreement.sign", null, bizParams);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String query(String outAgreementNo, String creditAgreementId) {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("out_agreement_no", outAgreementNo);
        bizParams.put("credit_agreement_id", creditAgreementId);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("zhima.credit.payafteruse.creditagreement.query", null, bizParams);
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
