package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.AccessTokenService;
import com.example.demo.weixin.api.bo.token.AccessToken;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    @Override
    public AccessToken getAccessToken(String grantType, String appid,
                                      String secret) throws RuntimeException {
        AccessToken accessToken;

        try {
            accessToken = JSON.parseObject(
                HttpUtil.get(MessageFormat.format(HTTPS_GET_URL, grantType, appid, secret)),
                AccessToken.class);
        } catch (Exception e) {
            logger.error(grantType + "&" + appid + "&" + secret, e);
            throw new RuntimeException(e);
        }

        if (accessToken == null) {
            throw new RuntimeException("access_token is null.");
        }

        if (accessToken.getErrCode() != 0) {
            throw new RuntimeException(accessToken.getErrMsg());
        }

        String token = accessToken.getAccessToken();

        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("access_token is blank.");
        }

        return accessToken;
    }

}
