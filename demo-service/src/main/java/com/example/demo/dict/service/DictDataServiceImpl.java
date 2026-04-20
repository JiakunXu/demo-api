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
import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictDataDO>
                                 implements DictDataService {

    @Autowired
    private RedisService<String, DictData> redisService;

    @Override
    public int countData(String typeId, String typeValue, DictData data) {
        if (StringUtils.isAllBlank(typeId, typeValue) || data == null) {
            return 0;
        }

        if (StringUtils.isNotBlank(typeId)) {
            data.setTypeId(new BigInteger(typeId));
        }

        data.setTypeValue(typeValue);

        return this.count(BeanUtil.copy(data, DictDataDO.class));
    }

    @Override
    public List<DictData> listDatas(String typeId, String typeValue) {
        DictData data = new DictData();
        data.setStatus(DictData.Status.ENABLE.value);
        data.setPageNo(1);
        data.setPageSize(99);

        return listDatas(typeId, typeValue, data);
    }

    @Override
    public List<DictData> listDatas(String typeId, String[] typeValue) {
        DictData data = new DictData();
        data.setStatus(DictData.Status.ENABLE.value);
        data.setPageNo(1);
        data.setPageSize(999);

        return listDatas(typeId, typeValue, data);
    }

    @Override
    public List<DictData> listDatas(String typeId, String typeValue, DictData data) {
        if (StringUtils.isAllBlank(typeId, typeValue) || data == null) {
            return List.of();
        }

        if (StringUtils.isNotBlank(typeId)) {
            data.setTypeId(new BigInteger(typeId));
        }

        data.setTypeValue(typeValue);

        List<DictData> list = BeanUtil.copy(this.list(BeanUtil.copy(data, DictDataDO.class)),
            DictData.class);

        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }

        return list;
    }

    @Override
    public List<DictData> listDatas(String typeId, String[] typeValue, DictData data) {
        if ((StringUtils.isBlank(typeId) && (typeValue == null || typeValue.length == 0))
            || data == null) {
            return null;
        }

        if (StringUtils.isNotBlank(typeId)) {
            data.setTypeId(new BigInteger(typeId));
        }

        data.setTypeValues(typeValue);

        return BeanUtil.copy(this.list(BeanUtil.copy(data, DictDataDO.class)), DictData.class);
    }

    @Override
    public DictData getData(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return getData(new BigInteger(id));
    }

    @Override
    public DictData getData(BigInteger id) {
        if (id == null) {
            return null;
        }

        return BeanUtil.copy(this.get(new DictDataDO(id)), DictData.class);
    }

    @Override
    public DictData getData(String typeValue, String value) {
        if (StringUtils.isAnyBlank(typeValue, value)) {
            return new DictData();
        }

        String key = typeValue + "&" + value;

        DictData data = null;

        try {
            data = redisService.get(RedisService.CACHE_KEY_DICT_TYPE + key);
        } catch (ServiceException e) {
            log.error(RedisService.CACHE_KEY_DICT_TYPE + "{}", key, e);
        }

        if (data != null) {
            return data;
        }

        DictDataDO dataDO = new DictDataDO();
        dataDO.setTypeValue(typeValue);
        dataDO.setValue(value);
        dataDO.setStatus(DictData.Status.ENABLE.value);

        data = BeanUtil.copy(this.get(dataDO), DictData.class);

        if (data == null) {
            return new DictData();
        }

        try {
            redisService.set(RedisService.CACHE_KEY_DICT_TYPE + key, data);
        } catch (ServiceException e) {
            log.error(RedisService.CACHE_KEY_DICT_TYPE + "{}", key, e);
        }

        return data;
    }

    @Override
    public DictData insertData(@NotNull BigInteger typeId, @NotNull DictData data,
                               @NotBlank String creator) {
        data.setTypeId(typeId);

        DictDataDO dataDO = BeanUtil.copy(data, DictDataDO.class);
        dataDO.setCreator(creator);

        try {
            this.insert(dataDO);
        } catch (Exception e) {
            log.error("{}", dataDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        data.setId(dataDO.getId());

        return data;
    }

    @Override
    public DictData updateData(@NotNull BigInteger id, @NotNull DictData data,
                               @NotBlank String modifier) {
        DictData before = getData(id);

        if (before == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        }

        data.setId(id);

        DictDataDO dataDO = BeanUtil.copy(data, DictDataDO.class);
        dataDO.setModifier(modifier);

        try {
            if (this.baseMapper.update0(dataDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("{}", dataDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        remove(before.getTypeValue() + "&" + before.getValue());

        return data;
    }

    @Override
    public DictData updateData(@NotNull BigInteger typeId, @NotBlank String typeValue,
                               @NotBlank String modifier) {
        List<DictData> list = listDatas(typeId.toString(), (String) null);

        if (!CollectionUtils.isEmpty(list)) {
            for (DictData item : list) {
                remove(item.getTypeValue() + "&" + item.getValue());
            }
        }

        DictDataDO dataDO = new DictDataDO();
        dataDO.setTypeId(typeId);
        dataDO.setTypeValue(typeValue);
        dataDO.setModifier(modifier);

        try {
            this.baseMapper.update1(dataDO);
        } catch (Exception e) {
            log.error("{}", dataDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(dataDO, DictData.class);
    }

    @Override
    public DictData deleteData(BigInteger typeId, BigInteger id, @NotBlank String modifier) {
        if (typeId == null && id == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        if (id != null) {
            DictData dictData = getData(id);
            if (dictData != null) {
                remove(dictData.getTypeValue() + "&" + dictData.getValue());
            }
        }

        if (typeId != null) {
            List<DictData> list = listDatas(typeId.toString(), (String) null);
            if (!CollectionUtils.isEmpty(list)) {
                for (DictData item : list) {
                    remove(item.getTypeValue() + "&" + item.getValue());
                }
            }
        }

        DictDataDO dataDO = new DictDataDO();
        dataDO.setId(id);
        dataDO.setTypeId(typeId);
        dataDO.setModifier(modifier);

        try {
            this.delete(dataDO);
        } catch (Exception e) {
            log.error("{}", dataDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(dataDO, DictData.class);
    }

    private void remove(String key) {
        try {
            redisService.remove(RedisService.CACHE_KEY_DICT_TYPE + key);
        } catch (Exception e) {
            log.error(RedisService.CACHE_KEY_DICT_TYPE + "{}", key, e);
        }
    }

}
