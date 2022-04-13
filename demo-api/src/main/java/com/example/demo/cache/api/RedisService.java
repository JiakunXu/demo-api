package com.example.demo.cache.api;

/**
 * Redis.
 *
 * @author JiakunXu
 */
public interface RedisService<K, V> extends CacheService<K, V> {

    /**
     * default_exp_time.
     */
    long   DEFAULT_EXP         = 24 * 60 * 60;

    String CACHE_KEY_DD_TOKEN  = "key_dd_token_";

    String CACHE_KEY_DD_TICKET = "key_dd_ticket_";

    String CACHE_KEY_TT_TOKEN  = "key_tt_token_";

    String CACHE_KEY_WX_TOKEN  = "key_wx_token_";

    String CACHE_KEY_WX_TICKET = "key_wx_ticket_";

}
