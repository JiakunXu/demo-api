package com.example.demo.dingtalk.manager;

import com.aliyun.dingtalkoauth2_1_0.Client;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.example.demo.dingtalk.api.AccessTokenService;
import com.example.demo.dingtalk.api.bo.AccessToken;
import com.example.demo.framework.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("com.example.demo.dingtalk.manager.accessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {

    @Override
    public AccessToken getAccessToken(String appKey, String appSecret) {
        Client client;

        try {
            client = new Client(new Config().setProtocol("https").setRegionId("central"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        GetAccessTokenRequest request = new GetAccessTokenRequest().setAppKey(appKey)
            .setAppSecret(appSecret);

        GetAccessTokenResponse response;

        try {
            response = client.getAccessToken(request);
        } catch (Exception e) {
            log.error("{}", request, e);

            TeaException err = e instanceof TeaException ? (TeaException) e
                : new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                throw new RuntimeException(err.message);
            } else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        return BeanUtil.copy(response.getBody(), AccessToken.class);
    }

}
