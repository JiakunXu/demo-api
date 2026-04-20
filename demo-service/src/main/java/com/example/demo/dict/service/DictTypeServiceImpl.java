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
import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictTypeDO>
                                 implements DictTypeService {

    @Autowired
    private DictDataService                dictDataService;

    @Autowired
    private RedisService<String, DictType> redisService;

    @Override
    public int countType(DictType type) {
        if (type == null) {
            return 0;
        }

        return this.count(BeanUtil.copy(type, DictTypeDO.class));
    }

    @Override
    public List<DictType> listTypes(DictType type) {
        if (type == null) {
            return null;
        }

        return BeanUtil.copy(this.list(BeanUtil.copy(type, DictTypeDO.class)), DictType.class);
    }

    @Override
    public DictType getType(String id, String value) {
        if (StringUtils.isBlank(id) && StringUtils.isBlank(value)) {
            return null;
        }

        String key = id + "@" + value;

        DictType type = null;

        try {
            type = redisService.get(RedisService.CACHE_KEY_DICT_TYPE + key);
        } catch (ServiceException e) {
            log.error(RedisService.CACHE_KEY_DICT_TYPE + "{}", key, e);
        }

        if (type != null) {
            return type;
        }

        DictTypeDO typeDO = new DictTypeDO();

        if (StringUtils.isNotBlank(id)) {
            typeDO.setId(new BigInteger(id));
        }

        typeDO.setValue(value);

        type = BeanUtil.copy(this.get(typeDO), DictType.class);

        if (type == null) {
            return null;
        }

        try {
            redisService.set(RedisService.CACHE_KEY_DICT_TYPE + key, type);
        } catch (ServiceException e) {
            log.error(RedisService.CACHE_KEY_DICT_TYPE + key, e);
        }

        return type;
    }

    @Override
    public DictType getType(BigInteger id, String value) {
        if (id == null && StringUtils.isBlank(value)) {
            return null;
        }

        DictTypeDO typeDO = new DictTypeDO();
        typeDO.setId(id);
        typeDO.setValue(value);

        return BeanUtil.copy(this.get(typeDO), DictType.class);
    }

    @Override
    public DictType insertType(@NotNull DictType type, @NotBlank String creator) {
        DictTypeDO typeDO = BeanUtil.copy(type, DictTypeDO.class);
        typeDO.setCreator(creator);

        try {
            this.insert(typeDO);
        } catch (Exception e) {
            log.error("{}", typeDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        type.setId(typeDO.getId());

        return type;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public DictType updateType(@NotNull BigInteger id, @NotNull DictType type,
                               @NotBlank String modifier) {
        DictType before = getType(id, null);

        if (before == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        }

        type.setId(id);

        DictTypeDO typeDO = BeanUtil.copy(type, DictTypeDO.class);
        typeDO.setModifier(modifier);

        try {
            if (this.update(typeDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("{}", typeDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        dictDataService.updateData(id, type.getValue(), modifier);

        remove(id + "@" + before.getValue());
        remove(id + "@null");
        remove("null@" + before.getValue());

        return type;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public DictType deleteType(@NotNull BigInteger id, @NotBlank String modifier) {
        DictType before = getType(id, null);

        if (before == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        }

        DictTypeDO typeDO = new DictTypeDO();
        typeDO.setId(id);
        typeDO.setModifier(modifier);

        try {
            this.delete(typeDO);
        } catch (Exception e) {
            log.error("{}", typeDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        dictDataService.deleteData(id, null, modifier);

        remove(id + "@" + before.getValue());
        remove(id + "@null");
        remove("null@" + before.getValue());

        return BeanUtil.copy(typeDO, DictType.class);
    }

    private void remove(String key) {
        try {
            redisService.remove(RedisService.CACHE_KEY_DICT_TYPE + key);
        } catch (Exception e) {
            log.error(RedisService.CACHE_KEY_DICT_TYPE + "{}", key, e);
        }
    }

}
