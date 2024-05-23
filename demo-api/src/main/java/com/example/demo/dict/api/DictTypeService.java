package com.example.demo.dict.api;

import com.example.demo.dict.api.bo.DictType;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
public interface DictTypeService {

    int countDictType(DictType dictType);

    List<DictType> listDictTypes(DictType dictType);

    DictType getDictType(String id, String value);

    DictType getDictType(BigInteger id, String value);

    DictType insertDictType(DictType dictType, String creator);

    DictType updateDictType(BigInteger id, DictType dictType, String modifier);

    DictType deleteDictType(BigInteger id, String modifier);

}
