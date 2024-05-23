package com.example.demo.dict.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.dict.api.DictDataService;
import com.example.demo.dict.api.bo.DictData;
import com.example.demo.dict.dao.dataobject.DictDataDO;
import com.example.demo.dict.dao.mapper.DictDataMapper;
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
public class DictDataServiceImpl implements DictDataService {

    private static final Logger            logger = LoggerFactory
        .getLogger(DictDataServiceImpl.class);

    @Autowired
    private RedisService<String, DictData> redisService;

    @Autowired
    private DictDataMapper                 dictDataMapper;

    @Override
    public int countDictData(String typeId, String typeValue, DictData dictData) {
        if (StringUtils.isAllBlank(typeId, typeValue) || dictData == null) {
            return 0;
        }

        if (StringUtils.isNotBlank(typeId)) {
            dictData.setTypeId(new BigInteger(typeId));
        }

        dictData.setTypeValue(typeValue);

        return count(BeanUtil.copy(dictData, DictDataDO.class));
    }

    @Override
    public List<DictData> listDictDatas(String typeId, String typeValue) {
        DictData dictData = new DictData();
        dictData.setStatus(DictData.Status.ENABLE.value);
        dictData.setPageNo(1);
        dictData.setPageSize(99);

        return listDictDatas(typeId, typeValue, dictData);
    }

    @Override
    public List<DictData> listDictDatas(String typeId, String[] typeValue) {
        DictData dictData = new DictData();
        dictData.setStatus(DictData.Status.ENABLE.value);
        dictData.setPageNo(1);
        dictData.setPageSize(999);

        return listDictDatas(typeId, typeValue, dictData);
    }

    @Override
    public List<DictData> listDictDatas(String typeId, String typeValue, DictData dictData) {
        if (StringUtils.isAllBlank(typeId, typeValue) || dictData == null) {
            return null;
        }

        if (StringUtils.isNotBlank(typeId)) {
            dictData.setTypeId(new BigInteger(typeId));
        }

        dictData.setTypeValue(typeValue);

        return BeanUtil.copy(list(BeanUtil.copy(dictData, DictDataDO.class)), DictData.class);
    }

    @Override
    public List<DictData> listDictDatas(String typeId, String[] typeValue, DictData dictData) {
        if ((StringUtils.isBlank(typeId) && (typeValue == null || typeValue.length == 0))
            || dictData == null) {
            return null;
        }

        if (StringUtils.isNotBlank(typeId)) {
            dictData.setTypeId(new BigInteger(typeId));
        }

        dictData.setTypeValues(typeValue);

        return BeanUtil.copy(list(BeanUtil.copy(dictData, DictDataDO.class)), DictData.class);
    }

    @Override
    public DictData getDictData(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return getDictData(new BigInteger(id));
    }

    @Override
    public DictData getDictData(BigInteger id) {
        if (id == null) {
            return null;
        }

        return BeanUtil.copy(get(new DictDataDO(id)), DictData.class);
    }

    @Override
    public DictData getDictData(String typeValue, String value) {
        if (StringUtils.isBlank(typeValue) || StringUtils.isBlank(value)) {
            return null;
        }

        String key = typeValue + "&" + value;

        DictData dictData = null;

        try {
            dictData = redisService.get(RedisService.CACHE_KEY_DICT_TYPE + key);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DICT_TYPE + key, e);
        }

        if (dictData != null) {
            return dictData;
        }

        DictDataDO dictDataDO = new DictDataDO();
        dictDataDO.setTypeValue(typeValue);
        dictDataDO.setValue(value);
        dictDataDO.setStatus(DictData.Status.ENABLE.value);

        dictData = BeanUtil.copy(get(dictDataDO), DictData.class);

        if (dictData == null) {
            return null;
        }

        try {
            redisService.set(RedisService.CACHE_KEY_DICT_TYPE + key, dictData);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DICT_TYPE + key, e);
        }

        return dictData;
    }

    @Override
    public DictData insertDictData(@NotNull BigInteger typeId, @NotNull DictData dictData,
                                   @NotBlank String creator) {
        dictData.setTypeId(typeId);

        DictDataDO dictDataDO = BeanUtil.copy(dictData, DictDataDO.class);
        dictDataDO.setCreator(creator);

        try {
            dictDataMapper.insert(dictDataDO);
        } catch (Exception e) {
            logger.error(dictDataDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        dictData.setId(dictDataDO.getId());

        return dictData;
    }

    @Override
    public DictData updateDictData(@NotNull BigInteger id, @NotNull DictData dictData,
                                   @NotBlank String modifier) {
        DictData before = getDictData(id);

        if (before == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        }

        dictData.setId(id);

        DictDataDO dictDataDO = BeanUtil.copy(dictData, DictDataDO.class);
        dictDataDO.setModifier(modifier);

        try {
            if (dictDataMapper.update0(dictDataDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR);
            }
        } catch (ServiceException e) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        } catch (Exception e) {
            logger.error(dictDataDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        remove(before.getTypeValue() + "&" + before.getValue());

        return dictData;
    }

    @Override
    public DictData updateDictData(@NotNull BigInteger typeId, @NotBlank String typeValue,
                                   @NotBlank String modifier) {
        List<DictData> list = listDictDatas(typeId.toString(), (String) null);

        if (list != null && !list.isEmpty()) {
            for (DictData item : list) {
                remove(item.getTypeValue() + "&" + item.getValue());
            }
        }

        DictDataDO dictDataDO = new DictDataDO();
        dictDataDO.setTypeId(typeId);
        dictDataDO.setTypeValue(typeValue);
        dictDataDO.setModifier(modifier);

        try {
            dictDataMapper.update1(dictDataDO);
        } catch (Exception e) {
            logger.error(dictDataDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(dictDataDO, DictData.class);
    }

    @Override
    public DictData deleteDictData(BigInteger typeId, BigInteger id, @NotBlank String modifier) {
        if (typeId == null && id == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        if (id != null) {
            DictData dictData = getDictData(id);
            if (dictData != null) {
                remove(dictData.getTypeValue() + "&" + dictData.getValue());
            }
        }

        if (typeId != null) {
            List<DictData> list = listDictDatas(typeId.toString(), (String) null);
            if (list != null && !list.isEmpty()) {
                for (DictData item : list) {
                    remove(item.getTypeValue() + "&" + item.getValue());
                }
            }
        }

        DictDataDO dictDataDO = new DictDataDO();
        dictDataDO.setId(id);
        dictDataDO.setTypeId(typeId);
        dictDataDO.setModifier(modifier);

        try {
            dictDataMapper.delete(dictDataDO);
        } catch (Exception e) {
            logger.error(dictDataDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(dictDataDO, DictData.class);
    }

    private void remove(String key) {
        try {
            redisService.remove(RedisService.CACHE_KEY_DICT_TYPE + key);
        } catch (Exception e) {
            logger.error(RedisService.CACHE_KEY_DICT_TYPE + key, e);
        }
    }

    private int count(DictDataDO dictDataDO) {
        try {
            return dictDataMapper.count(dictDataDO);
        } catch (Exception e) {
            logger.error(dictDataDO.toString(), e);
        }

        return 0;
    }

    private List<DictDataDO> list(DictDataDO dictDataDO) {
        try {
            return dictDataMapper.list(dictDataDO);
        } catch (Exception e) {
            logger.error(dictDataDO.toString(), e);
        }

        return null;
    }

    private DictDataDO get(DictDataDO dictDataDO) {
        try {
            return dictDataMapper.get(dictDataDO);
        } catch (Exception e) {
            logger.error(dictDataDO.toString(), e);
        }

        return null;
    }

}
