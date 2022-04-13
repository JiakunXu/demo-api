package com.example.demo.bytedance.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.bytedance.api.AccessTokenService;
import com.example.demo.bytedance.api.ao.AccessToken;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("accessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    @Override
    public AccessToken getAccessToken(String appId, String appSecret,
                                      String grantType) throws RuntimeException {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appid is null.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("secret is null.");
        }

        if (StringUtils.isBlank(grantType)) {
            throw new RuntimeException("grant_type is null.");
        }

        StringBuilder sb = new StringBuilder(AccessTokenService.HTTPS_TOKEN_URL);
        sb.append("?appid=").append(appId).append("&secret=").append(appSecret)
            .append("&grant_type=").append(grantType);

        AccessToken accessToken = null;

        try {
            accessToken = JSON.parseObject(HttpUtil.get(sb.toString()), AccessToken.class);
        } catch (Exception e) {
            logger.error(appId + "&" + appSecret + "&" + grantType, e);

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
