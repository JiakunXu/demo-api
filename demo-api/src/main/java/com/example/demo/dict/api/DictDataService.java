package com.example.demo.dict.api;

import com.example.demo.dict.api.bo.DictData;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
public interface DictDataService {

    int countDictData(String typeId, String typeValue, DictData dictData);

    List<DictData> listDictDatas(String typeId, String typeValue);

    List<DictData> listDictDatas(String typeId, String[] typeValue);

    List<DictData> listDictDatas(String typeId, String typeValue, DictData dictData);

    List<DictData> listDictDatas(String typeId, String[] typeValue, DictData dictData);

    DictData getDictData(String id);

    DictData getDictData(BigInteger id);

    DictData getDictData(String typeValue, Object value);

    DictData getDictData(String typeValue, String value);

    DictData insertDictData(BigInteger typeId, DictData dictData, String creator);

    DictData updateDictData(BigInteger id, DictData dictData, String modifier);

    DictData updateDictData(BigInteger typeId, String typeValue, String modifier);

    DictData deleteDictData(BigInteger typeId, BigInteger id, String modifier);

}
