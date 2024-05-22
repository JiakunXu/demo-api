package com.example.demo.security.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.security.api.RefreshTokenService;
import com.example.demo.user.api.bo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final Logger           logger = LoggerFactory
        .getLogger(RefreshTokenServiceImpl.class);

    @Autowired
    private RedisService<String, Boolean> redisService;

    @Override
    public boolean validate(User user) {
        if (user == null) {
            return false;
        }

        return redisService.hasKey(getKey(user));
    }

    @Override
    public void set(User user) {
        if (user == null) {
            return;
        }

        try {
            redisService.set(getKey(user), Boolean.TRUE,
                RedisService.CACHE_KEY_RE_TOKEN_DEFAULT_EXP);
        } catch (Exception e) {
            logger.error("set", e);
        }
    }

    @Override
    public void remove(User user) {
        if (user == null) {
            return;
        }

        try {
            redisService.remove(getKey(user));
        } catch (Exception e) {
            logger.error("remove", e);
        }
    }

    private String getKey(User user) {
        return RedisService.CACHE_KEY_RE_TOKEN + user.getId() + "@" + user.getRefreshToken();
    }

}
