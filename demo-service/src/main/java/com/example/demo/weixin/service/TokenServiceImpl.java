package com.example.demo.weixin.service;

import com.example.demo.cache.api.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.weixin.api.AccessTokenService;
import com.example.demo.weixin.api.TokenService;
import com.example.demo.weixin.api.bo.token.AccessToken;
import com.example.demo.framework.exception.ServiceException;

/**
 * @author JiakunXu
 */
@Service("com.example.demo.weixin.service.tokenService")
public class TokenServiceImpl implements TokenService {

    private static final Logger          logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private RedisService<String, String> redisService;

    @Autowired
    private AccessTokenService           accessTokenService;

    @Override
    public String getToken(String grantType, String appId,
                           String appSecret) throws RuntimeException {
        if (StringUtils.isBlank(grantType)) {
            throw new RuntimeException("grantType 不能为空.");
        }

        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appId 不能为空.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("appSecret 不能为空.");
        }

        String token = null;
        String key = grantType.trim() + "&" + appId.trim() + "&" + appSecret.trim();

        try {
            token = redisService.get(RedisService.CACHE_KEY_WX_TOKEN + key);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_WX_TOKEN + key, e);
        }

        if (StringUtils.isNotBlank(token)) {
            return token;
        }

        AccessToken accessToken = accessTokenService.getAccessToken(grantType, appId, appSecret);

        token = accessToken.getAccessToken();

        try {
            redisService.set(RedisService.CACHE_KEY_WX_TOKEN + key, token,
                accessToken.getExpiresIn());
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_WX_TOKEN + key, e);
        }

        return token;
    }
}
