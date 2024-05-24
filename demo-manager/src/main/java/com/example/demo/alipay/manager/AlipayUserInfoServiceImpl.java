package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.example.demo.alipay.api.AlipayUserInfoService;
import com.example.demo.alipay.api.bo.user.UserInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AlipayUserInfoServiceImpl implements AlipayUserInfoService {

    @Override
    public UserInfo getUserInfo(String authToken) {
        Map<String, String> textParams = new HashMap<>();
        textParams.put("auth_token", authToken);

        try {
            AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("alipay.user.info.share", textParams, null);
            if (ResponseChecker.success(response)) {
                return JSON.parseObject(JSON.parseObject(response.getHttpBody(), JSONObject.class)
                    .getString("alipay_user_info_share_response"), UserInfo.class);
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
