package com.example.demo.cache.api;

import com.example.demo.framework.exception.ServiceException;

/**
 * Redis.
 *
 * @author JiakunXu
 */
public interface RedisService<K, V> extends CacheService<K, V> {

    /**
     * default_exp_time.
     */
    long   DEFAULT_EXP                     = 24 * 60 * 60;

    String CACHE_KEY_DD_TICKET             = "key_dd_ticket@";

    String CACHE_KEY_DD_TOKEN              = "key_dd_token@";

    String CACHE_KEY_DICT_TYPE             = "key_dict_type@";

    String CACHE_KEY_TT_TOKEN              = "key_tt_token@";

    String CACHE_KEY_USER_TIME             = "key_user_time@";

    long   CACHE_KEY_USER_TIME_DEFAULT_EXP = 8 * 60 * 60;

    String CACHE_KEY_WX_TICKET             = "key_wx_ticket@";

    String CACHE_KEY_WX_TOKEN              = "key_wx_token@";

    boolean hasKey(K key);

    /**
     * expire.
     *
     * @param key
     * @return
     * @throws ServiceException
     */
    void expire(K key) throws ServiceException;

}
