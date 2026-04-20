package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.AccessTokenService;
import com.example.demo.weixin.api.bo.token.AccessToken;
import com.example.demo.framework.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Override
    public AccessToken getAccessToken(String grantType, String appid,
                                      String secret) throws RuntimeException {
        AccessToken accessToken;

        try {
            accessToken = JSON.parseObject(
                HttpUtil.get(MessageFormat.format(HTTPS_GET_URL, grantType, appid, secret)),
                AccessToken.class);
        } catch (Exception e) {
            log.error("{},{},{}", grantType, appid, secret, e);
            throw new RuntimeException(e.getMessage(), e);
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
