package com.example.demo.framework.service;

import com.example.demo.framework.mapper.BaseMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface IService<T> {

    /**
     * 默认批次提交数量
     */
    int DEFAULT_BATCH_SIZE = 1000;

    int count(T t);

    List<T> list(T t);

    T get(T t);

    default int insert(T t) {
        return getBaseMapper().insert(t);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean insertBatch(Collection<T> entityList) {
        return insertBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    boolean insertBatch(Collection<T> entityList, int batchSize);

    default int update(T t) {
        return getBaseMapper().update(t);
    }

    default int delete(T t) {
        return getBaseMapper().delete(t);
    }

    BaseMapper<T> getBaseMapper();

}
