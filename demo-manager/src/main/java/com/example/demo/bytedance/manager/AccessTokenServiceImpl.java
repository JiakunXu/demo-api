package com.example.demo.bytedance.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bytedance.api.AccessTokenService;
import com.example.demo.bytedance.api.bo.token.AccessToken;
import com.example.demo.bytedance.api.bo.token.Result;
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

        JSONObject object = new JSONObject();
        object.put("appid", appId);
        object.put("secret", appSecret);
        object.put("grant_type", grantType);

        Result result = null;

        try {
            result = JSON.parseObject(
                HttpUtil.post(AccessTokenService.HTTPS_TOKEN_URL, JSON.toJSONString(object)),
                Result.class);
        } catch (Exception e) {
            logger.error(appId + "&" + appSecret + "&" + grantType, e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null || result.getErrNo() == null) {
            throw new RuntimeException("access_token is null.");
        }

        if (result.getErrNo() != 0) {
            throw new RuntimeException(result.getErrTips());
        }

        AccessToken accessToken = result.getAccessToken();

        if (accessToken == null || StringUtils.isBlank(accessToken.getAccessToken())) {
            throw new RuntimeException("access_token is blank.");
        }

        return accessToken;
    }

}
