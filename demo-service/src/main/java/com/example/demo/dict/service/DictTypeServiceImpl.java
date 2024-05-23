package com.example.demo.dict.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.dict.api.DictDataService;
import com.example.demo.dict.api.DictTypeService;
import com.example.demo.dict.api.bo.DictType;
import com.example.demo.dict.dao.dataobject.DictTypeDO;
import com.example.demo.dict.dao.mapper.DictTypeMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class DictTypeServiceImpl implements DictTypeService {

    private static final Logger            logger = LoggerFactory
        .getLogger(DictTypeServiceImpl.class);

    @Autowired
    private DictDataService                dictDataService;

    @Autowired
    private RedisService<String, DictType> redisService;

    @Autowired
    private DictTypeMapper                 dictTypeMapper;

    @Override
    public int countDictType(DictType dictType) {
        if (dictType == null) {
            return 0;
        }

        return count(BeanUtil.copy(dictType, DictTypeDO.class));
    }

    @Override
    public List<DictType> listDictTypes(DictType dictType) {
        if (dictType == null) {
            return null;
        }

        return BeanUtil.copy(list(BeanUtil.copy(dictType, DictTypeDO.class)), DictType.class);
    }

    @Override
    public DictType getDictType(String id, String value) {
        if (StringUtils.isBlank(id) && StringUtils.isBlank(value)) {
            return null;
        }

        String key = id + "@" + value;

        DictType dictType = null;

        try {
            dictType = redisService.get(RedisService.CACHE_KEY_DICT_TYPE + key);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DICT_TYPE + key, e);
        }

        if (dictType != null) {
            return dictType;
        }

        DictTypeDO dictTypeDO = new DictTypeDO();

        if (StringUtils.isNotBlank(id)) {
            dictTypeDO.setId(new BigInteger(id));
        }

        dictTypeDO.setValue(value);

        dictType = BeanUtil.copy(get(dictTypeDO), DictType.class);

        if (dictType == null) {
            return null;
        }

        try {
            redisService.set(RedisService.CACHE_KEY_DICT_TYPE + key, dictType);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DICT_TYPE + key, e);
        }

        return dictType;
    }

    @Override
    public DictType getDictType(BigInteger id, String value) {
        if (id == null && StringUtils.isBlank(value)) {
            return null;
        }

        DictTypeDO dictTypeDO = new DictTypeDO();
        dictTypeDO.setId(id);
        dictTypeDO.setValue(value);

        return BeanUtil.copy(get(dictTypeDO), DictType.class);
    }

    @Override
    public DictType insertDictType(@NotNull DictType dictType, @NotBlank String creator) {
        DictTypeDO dictTypeDO = BeanUtil.copy(dictType, DictTypeDO.class);
        dictTypeDO.setCreator(creator);

        try {
            dictTypeMapper.insert(dictTypeDO);
        } catch (Exception e) {
            logger.error(dictTypeDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        dictType.setId(dictTypeDO.getId());

        return dictType;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public DictType updateDictType(@NotNull BigInteger id, @NotNull DictType dictType,
                                   @NotBlank String modifier) {
        DictType before = getDictType(id, null);

        if (before == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        }

        dictType.setId(id);

        DictTypeDO dictTypeDO = BeanUtil.copy(dictType, DictTypeDO.class);
        dictTypeDO.setModifier(modifier);

        try {
            if (dictTypeMapper.update(dictTypeDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(dictTypeDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        dictDataService.updateDictData(id, dictType.getValue(), modifier);

        remove(id + "@" + before.getValue());
        remove(id + "@null");
        remove("null@" + before.getValue());

        return dictType;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public DictType deleteDictType(@NotNull BigInteger id, @NotBlank String modifier) {
        DictType before = getDictType(id, null);

        if (before == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        }

        DictTypeDO dictTypeDO = new DictTypeDO();
        dictTypeDO.setId(id);
        dictTypeDO.setModifier(modifier);

        try {
            dictTypeMapper.delete(dictTypeDO);
        } catch (Exception e) {
            logger.error(dictTypeDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        dictDataService.deleteDictData(id, null, modifier);

        remove(id + "@" + before.getValue());
        remove(id + "@null");
        remove("null@" + before.getValue());

        return BeanUtil.copy(dictTypeDO, DictType.class);
    }

    private void remove(String key) {
        try {
            redisService.remove(RedisService.CACHE_KEY_DICT_TYPE + key);
        } catch (Exception e) {
            logger.error(RedisService.CACHE_KEY_DICT_TYPE + key, e);
        }
    }

    private int count(DictTypeDO dictTypeDO) {
        try {
            return dictTypeMapper.count(dictTypeDO);
        } catch (Exception e) {
            logger.error(dictTypeDO.toString(), e);
        }

        return 0;
    }

    private List<DictTypeDO> list(DictTypeDO dictTypeDO) {
        try {
            return dictTypeMapper.list(dictTypeDO);
        } catch (Exception e) {
            logger.error(dictTypeDO.toString(), e);
        }

        return null;
    }

    private DictTypeDO get(DictTypeDO dictTypeDO) {
        try {
            return dictTypeMapper.get(dictTypeDO);
        } catch (Exception e) {
            logger.error(dictTypeDO.toString(), e);
        }

        return null;
    }

}
