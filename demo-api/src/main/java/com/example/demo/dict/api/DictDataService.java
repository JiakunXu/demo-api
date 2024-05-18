package com.example.demo.dict.api;

import com.example.demo.dict.api.bo.DictData;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface DictDataService {

    List<DictData> listDictDatas(String typeValue);

    DictData getDictData(String typeValue, String value);

}
