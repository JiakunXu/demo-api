package com.example.demo.dict.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.dict.api.DictDataService;
import com.example.demo.dict.api.bo.DictData;
import com.example.demo.dict.dao.dataobject.DictDataDO;
import com.example.demo.dict.dao.mapper.DictDataMapper;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<DictData> listDictDatas(String typeValue) {
        if (StringUtils.isBlank(typeValue)) {
            return null;
        }

        DictDataDO dictDataDO = new DictDataDO();
        dictDataDO.setTypeValue(typeValue);
        dictDataDO.setPageNo(1);
        dictDataDO.setPageSize(99);

        return BeanUtil.copy(list(dictDataDO), DictData.class);
    }

    @Override
    public DictData getDictData(String typeValue, String value) {
        if (StringUtils.isAnyBlank(typeValue, value)) {
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

        dictData = BeanUtil.copy(get(dictDataDO), DictData.class);

        if (dictData == null) {
            return new DictData();
        }

        try {
            redisService.set(RedisService.CACHE_KEY_DICT_TYPE + key, dictData);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DICT_TYPE + key, e);
        }

        return dictData;
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
