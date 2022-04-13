package com.example.demo.dingtalk.service.impl;

import com.example.demo.api.cache.RedisService;
import com.example.demo.api.dingtalk.AccessTokenService;
import com.example.demo.api.dingtalk.TokenService;
import com.example.demo.api.dingtalk.ao.AccessToken;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("tokenService0")
public class TokenServiceImpl implements TokenService {

    private static final Logger          logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private RedisService<String, String> redisService;

    @Autowired
    private AccessTokenService           accessTokenService;

    @Override
    public String getToken(String appKey, String appSecret) throws RuntimeException {
        if (StringUtils.isBlank(appKey)) {
            throw new RuntimeException("appkey is null.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("appsecret is null.");
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

        AccessToken accessToken = accessTokenService.getAccessToken(appKey, appSecret);
        token = accessToken.getAccessToken();

        try {
            redisService.set(RedisService.CACHE_KEY_DD_TOKEN + key, token,
                accessToken.getExpiresIn());
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DD_TOKEN + key, e);
        }

        return token;
    }
}
