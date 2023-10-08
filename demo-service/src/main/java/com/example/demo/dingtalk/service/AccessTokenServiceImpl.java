package com.example.demo.dingtalk.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.dingtalk.api.Oauth2Service;
import com.example.demo.dingtalk.api.AccessTokenService;
import com.example.demo.dingtalk.api.bo.AccessToken;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accessTokenService3")
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final Logger          logger = LoggerFactory
        .getLogger(AccessTokenServiceImpl.class);

    @Autowired
    private RedisService<String, String> redisService;

    @Autowired
    private Oauth2Service                oauth2Service;

    @Override
    public String getAccessToken(String appKey, String appSecret) throws RuntimeException {
        if (StringUtils.isBlank(appKey)) {
            throw new RuntimeException("app_key is null.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("app_secret is null.");
        }

        String token = null;

        String key = appKey.trim() + "&" + appSecret.trim();

        try {
            token = redisService.get(RedisService.CACHE_KEY_DD_TOKEN + key);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DD_TOKEN + key, e);
        }

        if (StringUtils.isNotBlank(token)) {
            return token;
        }

        AccessToken accessToken = oauth2Service.getAccessToken(appKey, appSecret);

        token = accessToken.getAccessToken();

        try {
            redisService.set(RedisService.CACHE_KEY_DD_TOKEN + key, token,
                accessToken.getExpireIn() - 5 * 60);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DD_TOKEN + key, e);
        }

        return token;
    }

}
