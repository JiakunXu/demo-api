package com.example.demo.dict.api;

import com.example.demo.dict.api.bo.DictType;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
public interface DictTypeService {

    long countType(DictType type);

    List<DictType> listTypes(DictType type);

    DictType getType(String id, String value);

    DictType getType(BigInteger id, String value);

    DictType insertType(DictType type, String createBy);

    DictType updateType(BigInteger id, DictType type, String updateBy);

    DictType deleteType(BigInteger id, String updateBy);

}
