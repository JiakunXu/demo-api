package com.example.demo.api.cache;

/**
 * Redis.
 *
 * @author JiakunXu
 */
public interface RedisService<K, V> extends CacheService<K, V> {

    /**
     * default_exp_time.
     */
    int DEFAULT_EXP = 24 * 60 * 60;

}
