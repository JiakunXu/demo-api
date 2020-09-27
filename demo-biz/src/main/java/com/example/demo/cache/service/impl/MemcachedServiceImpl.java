package com.example.demo.cache.service.impl;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.example.demo.api.cache.MemcachedService;
import com.example.demo.api.cache.bo.CacheStats;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.exception.SystemException;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * cahce.
 * 
 * @author xujiakun
 * 
 */
@Service
public class MemcachedServiceImpl implements MemcachedService {

    private Logger          logger = LoggerFactory.getLogger(MemcachedServiceImpl.class);

    @Autowired
    private MemcachedClient memcachedClient;

    @Override
    public Object add(String key, Object value) {
        return add(key, value, MemcachedService.DEFAULT_EXP);
    }

    @Override
    public Object add(String key, Object value, Date expiry) {
        if (expiry == null) {
            return add(key, value);
        }

        return add(key, value,
            Seconds.secondsBetween(new DateTime(new Date()), new DateTime(expiry)).getSeconds());
    }

    @Override
    public Object add(String key, Object value, int exp) {
        try {
            if (memcachedClient.add(key, exp, value)) {
                return value;
            }
        } catch (TimeoutException e) {
            logger.error("add", e);
        } catch (InterruptedException e) {
            logger.error("add", e);
        } catch (MemcachedException e) {
            logger.error("add", e);
        }

        throw new ServiceException("memcached add.");
    }

    @Override
    public Object set(String key, Object value) {
        return set(key, value, MemcachedService.DEFAULT_EXP);
    }

    @Override
    public Object set(String key, Object value, Date expiry) {
        if (expiry == null) {
            return set(key, value);
        }

        return set(key, value,
            Seconds.secondsBetween(new DateTime(new Date()), new DateTime(expiry)).getSeconds());
    }

    @Override
    public Object set(String key, Object value, int exp) {
        try {
            if (memcachedClient.set(key, exp, value)) {
                return value;
            }
        } catch (TimeoutException e) {
            logger.error("set", e);
        } catch (InterruptedException e) {
            logger.error("set", e);
        } catch (MemcachedException e) {
            logger.error("set", e);
        }

        throw new ServiceException("memcached set.");
    }

    @Override
    public Object replace(String key, Object value) {
        return replace(key, value, MemcachedService.DEFAULT_EXP);
    }

    @Override
    public Object replace(String key, Object value, Date expiry) {
        if (expiry == null) {
            return replace(key, value);
        }

        return replace(key, value,
            Seconds.secondsBetween(new DateTime(new Date()), new DateTime(expiry)).getSeconds());
    }

    @Override
    public Object replace(String key, Object value, int exp) {
        try {
            if (memcachedClient.replace(key, exp, value)) {
                return value;
            }
        } catch (TimeoutException e) {
            logger.error("replace", e);
        } catch (InterruptedException e) {
            logger.error("replace", e);
        } catch (MemcachedException e) {
            logger.error("replace", e);
        }

        throw new ServiceException("memcached replace.");
    }

    @Override
    public Object get(String key) {
        try {
            return memcachedClient.get(key);
        } catch (TimeoutException e) {
            logger.error("get", e);
        } catch (InterruptedException e) {
            logger.error("get", e);
        } catch (MemcachedException e) {
            logger.error("get", e);
        }

        throw new ServiceException("memcached get.");
    }

    /**
     * remove 可能存在false的正常情况.
     */
    @Override
    public void remove(String key) {
        try {
            memcachedClient.delete(key);
            return;
        } catch (TimeoutException e) {
            logger.error("remove", e);
        } catch (InterruptedException e) {
            logger.error("remove", e);
        } catch (MemcachedException e) {
            logger.error("remove", e);
        }

        throw new ServiceException("memcached remove.");
    }

    @Override
    public long incr(String key, int inc) {
        try {
            return memcachedClient.incr(key, inc);
        } catch (TimeoutException e) {
            logger.error("incr", e);
        } catch (InterruptedException e) {
            logger.error("incr", e);
        } catch (MemcachedException e) {
            logger.error("incr", e);
        }

        throw new ServiceException("memcached incr.");
    }

    @Override
    public long incr(String key, long inc) {
        try {
            return memcachedClient.incr(key, inc);
        } catch (TimeoutException e) {
            logger.error("incr", e);
        } catch (InterruptedException e) {
            logger.error("incr", e);
        } catch (MemcachedException e) {
            logger.error("incr", e);
        }

        throw new ServiceException("memcached incr.");
    }

    @Override
    public long decr(String key, int decr) {
        try {
            return memcachedClient.decr(key, decr);
        } catch (TimeoutException e) {
            logger.error("decr", e);
        } catch (InterruptedException e) {
            logger.error("decr", e);
        } catch (MemcachedException e) {
            logger.error("decr", e);
        }

        throw new ServiceException("memcached decr.");
    }

    @Override
    public long decr(String key, long decr) {
        try {
            return memcachedClient.decr(key, decr);
        } catch (TimeoutException e) {
            logger.error("decr", e);
        } catch (InterruptedException e) {
            logger.error("decr", e);
        } catch (MemcachedException e) {
            logger.error("decr", e);
        }

        throw new ServiceException("memcached decr.");
    }

    @Override
    public void flushAll(InetSocketAddress address) throws SystemException {
        try {
            memcachedClient.flushAll(address);
        } catch (MemcachedException e) {
            logger.error("flushAll", e);
            throw new SystemException("memcached flushAll.");
        } catch (InterruptedException e) {
            logger.error("flushAll", e);
            throw new SystemException("memcached flushAll.");
        } catch (TimeoutException e) {
            logger.error("flushAll", e);
            throw new SystemException("memcached flushAll.");
        }
    }

    @Override
    public List<CacheStats> getStats() {
        Map<InetSocketAddress, Map<String, String>> maps = getStatsDetail();

        List<CacheStats> cacheStatsList = new ArrayList<CacheStats>();

        for (Map.Entry<InetSocketAddress, Map<String, String>> m : maps.entrySet()) {
            CacheStats cacheStats = new CacheStats();

            InetSocketAddress address = m.getKey();
            cacheStats.setAddress(address.toString());
            cacheStats.setHostAddress(address.getAddress().getHostAddress());
            cacheStats.setHostName(address.getAddress().getHostName());
            cacheStats.setPort(String.valueOf(address.getPort()));

            Map<String, String> map = m.getValue();
            cacheStats.setDeleteHits(map.get("delete_hits"));
            cacheStats.setBytes(map.get("bytes"));
            cacheStats.setTotalItems(map.get("total_items"));
            cacheStats.setRusageSystem(map.get("rusage_system"));
            cacheStats.setTouchMisses(map.get("touch_misses"));
            cacheStats.setCmdTouch(map.get("cmd_touch"));
            cacheStats.setListenDisabledNum(map.get("listen_disabled_num"));
            cacheStats.setAuthErrors(map.get("auth_errors"));
            cacheStats.setEvictions(map.get("evictions"));
            cacheStats.setVersion(map.get("version"));
            cacheStats.setPointerSize(map.get("pointer_size"));
            cacheStats.setTime(map.get("time"));
            cacheStats.setIncrHits(map.get("incr_hits"));
            cacheStats.setThreads(map.get("threads"));
            cacheStats.setExpiredUnfetched(map.get("expired_unfetched"));
            cacheStats.setLimitMaxbytes(map.get("limit_maxbytes"));
            cacheStats.setHashIsExpanding(map.get("hash_is_expanding"));
            cacheStats.setBytesRead(map.get("bytes_read"));
            cacheStats.setCurrConnections(map.get("curr_connections"));
            cacheStats.setGetMisses(map.get("get_misses"));
            cacheStats.setReclaimed(map.get("reclaimed"));
            cacheStats.setBytesWritten(map.get("bytes_written"));
            cacheStats.setHashPowerLevel(map.get("hash_power_level"));
            cacheStats.setConnectionStructures(map.get("connection_structures"));
            cacheStats.setCasHits(map.get("cas_hits"));
            cacheStats.setDeleteMisses(map.get("delete_misses"));
            cacheStats.setTotalConnections(map.get("total_connections"));
            cacheStats.setRusageUser(map.get("rusage_user"));
            cacheStats.setCmdFlush(map.get("cmd_flush"));
            cacheStats.setLibevent(map.get("libevent"));
            cacheStats.setUptime(map.get("uptime"));
            cacheStats.setReservedFds(map.get("reserved_fds"));
            cacheStats.setTouchHits(map.get("touch_hits"));
            cacheStats.setCasBadval(map.get("cas_badval"));
            cacheStats.setPid(map.get("pid"));
            cacheStats.setGetHits(map.get("get_hits"));
            cacheStats.setCurrItems(map.get("curr_items"));
            cacheStats.setCasMisses(map.get("cas_misses"));
            cacheStats.setAcceptingConns(map.get("accepting_conns"));
            cacheStats.setEvictedUnfetched(map.get("evicted_unfetched"));
            cacheStats.setCmdGet(map.get("cmd_get"));
            cacheStats.setCmdSet(map.get("cmd_set"));
            cacheStats.setAuthCmds(map.get("auth_cmds"));
            cacheStats.setIncrMisses(map.get("incr_misses"));
            cacheStats.setHashBytes(map.get("hash_bytes"));
            cacheStats.setDecrMisses(map.get("decr_misses"));
            cacheStats.setDecrHits(map.get("decr_hits"));
            cacheStats.setConnYields(map.get("conn_yields"));

            cacheStatsList.add(cacheStats);
        }

        return cacheStatsList;
    }

    private Map<InetSocketAddress, Map<String, String>> getStatsDetail() {
        try {
            return memcachedClient.getStats();
        } catch (MemcachedException e) {
            logger.error("getStats", e);
        } catch (InterruptedException e) {
            logger.error("getStats", e);
        } catch (TimeoutException e) {
            logger.error("getStats", e);
        }

        throw new ServiceException("memcached getStatsDetail.");
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

}
