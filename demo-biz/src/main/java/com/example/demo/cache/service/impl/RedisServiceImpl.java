package com.example.demo.cache.service.impl;

import com.example.demo.api.cache.RedisService;
import com.example.demo.framework.exception.ServiceException;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author JiakunXu
 */
@Service
public class RedisServiceImpl implements RedisService {

    private static final Logger           logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object add(String key, Object value) throws ServiceException {
        return add(key, value, RedisService.DEFAULT_EXP);
    }

    @Override
    public Object add(String key, Object value, Date expiry) throws ServiceException {
        if (expiry == null) {
            return add(key, value);
        }

        return add(key, value,
            Seconds.secondsBetween(new DateTime(new Date()), new DateTime(expiry)).getSeconds());
    }

    @Override
    public Object add(String key, Object value, int exp) throws ServiceException {
        try {
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, exp,
                TimeUnit.SECONDS);
            if (result != null && result) {
                return value;
            }
        } catch (Exception e) {
            logger.error("add", e);
        }

        throw new ServiceException("redis add.");
    }

    @Override
    public Object set(String key, Object value) throws ServiceException {
        return set(key, value, RedisService.DEFAULT_EXP);
    }

    @Override
    public Object set(String key, Object value, Date expiry) throws ServiceException {
        if (expiry == null) {
            return set(key, value);
        }

        return set(key, value,
            Seconds.secondsBetween(new DateTime(new Date()), new DateTime(expiry)).getSeconds());
    }

    @Override
    public Object set(String key, Object value, int exp) throws ServiceException {
        try {
            redisTemplate.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
            return value;
        } catch (Exception e) {
            logger.error("set", e);
        }

        throw new ServiceException("redis set.");
    }

    @Override
    public Object replace(String key, Object value) throws ServiceException {
        return null;
    }

    @Override
    public Object replace(String key, Object value, Date expiry) throws ServiceException {
        return null;
    }

    @Override
    public Object replace(String key, Object value, int exp) throws ServiceException {
        return null;
    }

    @Override
    public Object get(String key) throws ServiceException {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("get", e);
        }

        throw new ServiceException("redis get.");
    }

    @Override
    public Object remove(String key) throws ServiceException {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("remove", e);
        }

        throw new ServiceException("redis remove.");
    }

}
