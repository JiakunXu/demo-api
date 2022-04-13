package com.example.demo.weixin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.weixin.AccessTokenService;
import com.example.demo.api.weixin.ao.AccessToken;
import com.example.demo.framework.util.HttpUtil;

/**
 * @author JiakunXu
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    @Override
    public AccessToken getAccessToken(String grantType, String appId,
                                      String appSecret) throws RuntimeException {
        if (StringUtils.isBlank(grantType)) {
            throw new RuntimeException("grant_type cannot be null.");
        }

        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appid cannot be null.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("secret cannot be null.");
        }

        AccessToken accessToken = null;

        try {
            accessToken = JSON.parseObject(
                HttpUtil
                    .get(AccessTokenService.HTTPS_TOKEN_URL.replace("$grantType$", grantType.trim())
                        .replace("$appId$", appId.trim()).replace("$appSecret$", appSecret.trim())),
                AccessToken.class);
        } catch (Exception e) {
            logger.error(grantType + "&" + appId + "&" + appSecret, e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (accessToken == null) {
            throw new RuntimeException("access_token is null.");
        }

        String errCode = accessToken.getErrCode();
        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(accessToken.getErrMsg());
        }

        String token = accessToken.getAccessToken();

        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("access_token is blank.");
        }

        return accessToken;
    }

}
