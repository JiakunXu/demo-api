package com.example.demo.dingtalk.manager;

import com.alibaba.fastjson2.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.example.demo.dingtalk.api.AccessTokenService;
import com.example.demo.dingtalk.api.bo.token.AccessToken;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("accessTokenService0")
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    @Override
    public AccessToken getAccessToken(String appKey, String appSecret) {
        if (StringUtils.isBlank(appKey)) {
            throw new RuntimeException("appkey is null.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("appsecret is null.");
        }

        DefaultDingTalkClient client = new DefaultDingTalkClient(
            AccessTokenService.HTTPS_TOKEN_URL);

        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");

        OapiGettokenResponse response;

        try {
            response = client.execute(request);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(request), e);
            throw new RuntimeException(e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        String errCode = response.getErrorCode();

        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(response.getErrmsg());
        }

        String token = response.getAccessToken();

        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("access_token is blank.");
        }

        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(token);
        accessToken.setExpiresIn(response.getExpiresIn());

        return accessToken;
    }

}
