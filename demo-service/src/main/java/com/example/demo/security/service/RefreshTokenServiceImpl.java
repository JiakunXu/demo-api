package com.example.demo.security.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.security.api.RefreshTokenService;
import com.example.demo.user.api.bo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RedisService<String, Boolean> redisService;

    @Override
    public boolean validate(UserDetails user) {
        if (user == null) {
            return false;
        }

        return redisService.hasKey(getKey(user));
    }

    @Override
    public void set(UserDetails user) {
        if (user == null) {
            return;
        }

        try {
            redisService.set(getKey(user), Boolean.TRUE,
                RedisService.CACHE_KEY_REFRESH_TOKEN_DEFAULT_EXP);
        } catch (Exception e) {
            log.error("set", e);
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
            log.error("remove", e);
        }
    }

    private String getKey(UserDetails user) {
        if (user instanceof User) {
            return getKey((User) user);
        }

        return null;
    }

    private String getKey(User user) {
        return RedisService.CACHE_KEY_REFRESH_TOKEN + user.getId() + "@" + user.getRefreshToken();
    }

}
