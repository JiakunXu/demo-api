package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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
@Service("com.example.demo.bytedance.manager.accessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    @Override
    public AccessToken getAccessToken(String appId, String appSecret,
                                      String grantType) throws RuntimeException {
        JSONObject data = new JSONObject();
        data.put("appid", appId);
        data.put("secret", appSecret);
        data.put("grant_type", grantType);

        Result result;

        try {
            result = JSON.parseObject(
                HttpUtil.post(AccessTokenService.HTTPS_POST_URL, JSON.toJSONString(data)),
                Result.class);
        } catch (Exception e) {
            logger.error(appId + "&" + appSecret + "&" + grantType, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (result == null) {
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
