package com.example.demo.dict.dao.mapper;

import com.example.demo.dict.dao.dataobject.DictDataDO;
import com.example.demo.framework.mapper.BaseMapper;

public interface DictDataMapper extends BaseMapper<DictDataDO> {

    int update0(DictDataDO dictDataDO);

    int update1(DictDataDO dictDataDO);

}
