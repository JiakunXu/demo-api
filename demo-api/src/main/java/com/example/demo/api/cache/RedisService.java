package com.example.demo.api.cache;

/**
 * Redis.
 *
 * @author JiakunXu
 */
public interface RedisService extends CacheService<String, Object> {

    /**
     * default_exp_time.
     */
    int DEFAULT_EXP = 24 * 60 * 60;

}
