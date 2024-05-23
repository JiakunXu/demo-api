package com.example.demo.config.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.config.api.ConfigService;
import com.example.demo.config.api.bo.Config;
import com.example.demo.config.dao.dataobject.ConfigDO;
import com.example.demo.config.dao.mapper.ConfigMapper;
import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    private static final Logger          logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private RedisService<String, Config> redisService;

    @Autowired
    private ConfigMapper                 configMapper;

    @Override
    public int countConfig(Config config) {
        if (config == null) {
            return 0;
        }

        return count(BeanUtil.copy(config, ConfigDO.class));
    }

    @Override
    public List<Config> listConfigs(Config config) {
        if (config == null) {
            return null;
        }

        return BeanUtil.copy(list(BeanUtil.copy(config, ConfigDO.class)), Config.class);
    }

    @Override
    public Config getConfig(String id, String key) {
        if (StringUtils.isBlank(id) && StringUtils.isBlank(key)) {
            return null;
        }

        ConfigDO configDO = new ConfigDO();

        if (StringUtils.isNotBlank(id)) {
            configDO.setId(new BigInteger(id));
        }

        configDO.setKey(key);

        return BeanUtil.copy(get(configDO), Config.class);
    }

    @Override
    public Config insertConfig(@NotNull Config config, @NotBlank String creator) {
        ConfigDO configDO = BeanUtil.copy(config, ConfigDO.class);
        configDO.setCreator(creator);

        try {
            configMapper.insert(configDO);
        } catch (Exception e) {
            logger.error(configDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        config.setId(configDO.getId());

        return config;
    }

    @Override
    public Config updateConfig(@NotNull BigInteger id, @NotNull Config config,
                               @NotBlank String modifier) {
        config.setId(id);

        ConfigDO configDO = BeanUtil.copy(config, ConfigDO.class);
        configDO.setModifier(modifier);

        try {
            if (configMapper.update(configDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(configDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return config;
    }

    @Override
    public Config deleteConfig(@NotNull BigInteger id, @NotBlank String modifier) {
        ConfigDO configDO = new ConfigDO();
        configDO.setId(id);
        configDO.setModifier(modifier);

        try {
            configMapper.delete(configDO);
        } catch (Exception e) {
            logger.error(configDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(configDO, Config.class);
    }

    private int count(ConfigDO configDO) {
        try {
            return configMapper.count(configDO);
        } catch (Exception e) {
            logger.error(configDO.toString(), e);
        }

        return 0;
    }

    private List<ConfigDO> list(ConfigDO configDO) {
        try {
            return configMapper.list(configDO);
        } catch (Exception e) {
            logger.error(configDO.toString(), e);
        }

        return null;
    }

    private ConfigDO get(ConfigDO configDO) {
        try {
            return configMapper.get(configDO);
        } catch (Exception e) {
            logger.error(configDO.toString(), e);
        }

        return null;
    }

}
