package com.example.demo.weixin.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.weixin.api.AccessTokenService;
import com.example.demo.weixin.api.TokenService;
import com.example.demo.weixin.api.bo.token.AccessToken;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String key = grantType + "&" + appId + "&" + appSecret;

        String token = null;

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
                accessToken.getExpiresIn() - 5 * 60);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_WX_TOKEN + key, e);
        }

        return token;
    }

}
