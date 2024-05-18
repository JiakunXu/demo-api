package com.example.demo.dict.service;

import com.example.demo.dict.api.DictTypeService;
import com.example.demo.dict.api.bo.DictType;
import com.example.demo.dict.dao.dataobject.DictTypeDO;
import com.example.demo.dict.dao.mapper.DictTypeMapper;
import com.example.demo.framework.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictTypeServiceImpl implements DictTypeService {

    private static final Logger logger = LoggerFactory.getLogger(DictTypeServiceImpl.class);

    @Autowired
    private DictTypeMapper      dictTypeMapper;

    @Override
    public DictType getDictType(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return BeanUtil.copy(get(new DictTypeDO(value)), DictType.class);
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
