package com.example.demo.cache.manager;

import com.example.demo.cache.api.RedisService;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class RedisServiceImpl<K, V> implements RedisService<K, V> {

    @Resource
    private RedisTemplate<K, V> redisTemplate;

    @Override
    public V add(K key, V value) throws ServiceException {
        return add(key, value, RedisService.DEFAULT_EXP);
    }

    @Override
    public V add(K key, V value, Date expiry) throws ServiceException {
        if (expiry == null) {
            return add(key, value);
        }

        return add(key, value,
            Seconds.secondsBetween(new DateTime(new Date()), new DateTime(expiry)).getSeconds());
    }

    @Override
    public V add(K key, V value, long timeout) throws ServiceException {
        try {
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, timeout,
                TimeUnit.SECONDS);
            if (result != null && result) {
                return value;
            }
        } catch (Exception e) {
            log.error("add", e);
        }

        throw new ServiceException(Constants.SERVICE_UNAVAILABLE, "redis add.");
    }

    @Override
    public V set(K key, V value) throws ServiceException {
        return set(key, value, RedisService.DEFAULT_EXP);
    }

    @Override
    public V set(K key, V value, Date expiry) throws ServiceException {
        if (expiry == null) {
            return set(key, value);
        }

        return set(key, value,
            Seconds.secondsBetween(new DateTime(new Date()), new DateTime(expiry)).getSeconds());
    }

    @Override
    public V set(K key, V value, long timeout) throws ServiceException {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            return value;
        } catch (Exception e) {
            log.error("set", e);
        }

        throw new ServiceException(Constants.SERVICE_UNAVAILABLE, "redis set.");
    }

    @Override
    public V replace(K key, V value) throws ServiceException {
        return null;
    }

    @Override
    public V replace(K key, V value, Date expiry) throws ServiceException {
        return null;
    }

    @Override
    public V replace(K key, V value, long timeout) throws ServiceException {
        return null;
    }

    @Override
    public V get(K key) throws ServiceException {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("get", e);
        }

        throw new ServiceException(Constants.SERVICE_UNAVAILABLE, "redis get.");
    }

    @Override
    public void remove(K key) throws ServiceException {
        try {
            redisTemplate.delete(key);
            return;
        } catch (Exception e) {
            log.error("remove", e);
        }

        throw new ServiceException(Constants.SERVICE_UNAVAILABLE, "redis remove.");
    }

    @Override
    public boolean hasKey(K key) {
        try {
            Boolean result = redisTemplate.hasKey(key);
            if (result != null && result) {
                return true;
            }
        } catch (Exception e) {
            log.error("hasKey", e);
        }

        return false;
    }

    @Override
    public void expire(K key) throws ServiceException {
        try {
            Boolean result = redisTemplate.expire(key, RedisService.DEFAULT_EXP, TimeUnit.SECONDS);
            if (result != null && result) {
                return;
            }
        } catch (Exception e) {
            log.error("expire", e);
        }

        throw new ServiceException(Constants.SERVICE_UNAVAILABLE, "redis expire.");
    }

}
