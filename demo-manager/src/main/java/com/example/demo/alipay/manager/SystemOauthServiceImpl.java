package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.easysdk.base.oauth.models.AlipaySystemOauthTokenResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.example.demo.alipay.api.SystemOauthService;
import com.example.demo.alipay.api.bo.token.OauthToken;
import org.springframework.stereotype.Service;

@Service
public class SystemOauthServiceImpl implements SystemOauthService {

    @Override
    public OauthToken getToken(String code) {
        try {
            AlipaySystemOauthTokenResponse response = Factory.Base.OAuth().getToken(code);
            if (ResponseChecker.success(response)) {
                return JSON.parseObject(JSON.parseObject(response.getHttpBody(), JSONObject.class)
                    .getString("alipay_system_oauth_token_response"), OauthToken.class);
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
