package com.example.demo.bytedance.service;

import com.example.demo.bytedance.api.AccessTokenService;
import com.example.demo.bytedance.api.TokenService;
import com.example.demo.bytedance.api.ao.AccessToken;
import com.example.demo.cache.api.RedisService;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    private static final Logger          logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private RedisService<String, String> redisService;

    @Autowired
    private AccessTokenService           accessTokenService;

    @Override
    public String getToken(String appId, String appSecret,
                           String grantType) throws RuntimeException {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appId 不能为空.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("appSecret 不能为空.");
        }

        if (StringUtils.isBlank(grantType)) {
            throw new RuntimeException("grantType 不能为空.");
        }

        String token = null;
        String key = appId.trim() + "&" + appSecret.trim() + "&" + grantType.trim();

        try {
            token = redisService.get(RedisService.CACHE_KEY_TT_TOKEN + key);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_TT_TOKEN + key, e);
        }

        if (StringUtils.isNotBlank(token)) {
            return token;
        }

        AccessToken accessToken = accessTokenService.getAccessToken(appId, appSecret, grantType);
        token = accessToken.getAccessToken();

        try {
            redisService.set(RedisService.CACHE_KEY_TT_TOKEN + key, token,
                accessToken.getExpiresIn());
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_TT_TOKEN + key, e);
        }

        return token;
    }

}
