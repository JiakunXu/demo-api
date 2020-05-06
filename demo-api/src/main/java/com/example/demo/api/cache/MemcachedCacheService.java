package com.example.demo.api.cache;

import com.example.demo.api.cache.bo.CacheStats;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.exception.SystemException;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * MemcachedCache.
 * 
 * @author JiakunXu
 * 
 */
public interface MemcachedCacheService extends CacheService<String, Object> {

    /**
     * default_exp_time.
     */
    int    DEFAULT_EXP         = 24 * 60 * 60;

    String CACHE_KEY_WX_TOKEN  = "key_wx_token_";

    String CACHE_KEY_WX_TICKET = "key_wx_ticket_";

    String CACHE_KEY_TT_TOKEN  = "key_tt_token_";

    /**
     * incr.
     * 
     * @param key
     * @param inc
     * @return
     * @throws ServiceException
     */
    long incr(String key, int inc) throws ServiceException;

    /**
     * incr.
     * 
     * @param key
     * @param inc
     * @return
     * @throws ServiceException
     */
    long incr(String key, long inc) throws ServiceException;

    /**
     * decr.
     * 
     * @param key
     * @param decr
     * @return
     * @throws ServiceException
     */
    long decr(String key, int decr) throws ServiceException;

    /**
     * decr.
     * 
     * @param key
     * @param decr
     * @return
     * @throws ServiceException
     */
    long decr(String key, long decr) throws ServiceException;

    /**
     * flushAll.
     * 
     * @param address
     * @throws SystemException
     */
    void flushAll(InetSocketAddress address) throws SystemException;

    /**
     * cache stats.
     * 
     * @return
     * @throws ServiceException
     */
    List<CacheStats> getStats() throws ServiceException;

}
