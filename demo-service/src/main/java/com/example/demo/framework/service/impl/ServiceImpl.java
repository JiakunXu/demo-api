package com.example.demo.framework.service.impl;

import com.baomidou.mybatisplus.core.metadata.MapperProxyMetadata;
import com.baomidou.mybatisplus.core.toolkit.MybatisUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.demo.framework.mapper.BaseMapper;
import com.example.demo.framework.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
public class ServiceImpl<M extends BaseMapper<T>, T> implements IService<T> {

    protected final Log                logger        = LogFactory.getLog(getClass());

    protected final Class<?>[]         typeArguments = GenericTypeResolver
        .resolveTypeArguments(getClass(), ServiceImpl.class);

    @Autowired
    protected M                        baseMapper;

    private volatile SqlSessionFactory sqlSessionFactory;

    @Override
    public int count(T t) {
        try {
            return this.baseMapper.count(t);
        } catch (Exception e) {
            log.error("{}", t, e);
        }

        return 0;
    }

    @Override
    public List<T> list(T t) {
        try {
            return this.baseMapper.list(t);
        } catch (Exception e) {
            log.error("{}", t, e);
        }

        return List.of();
    }

    @Override
    public T get(T t) {
        try {
            return this.baseMapper.get(t);
        } catch (Exception e) {
            log.error("{}", t, e);
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(Collection<T> entityList, int batchSize) {
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession
            .insert(this.typeArguments[0].getName() + "." + "insert", entity));
    }

    protected <E> boolean executeBatch(Collection<E> list, int batchSize,
                                       BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(getSqlSessionFactory(), this.logger, list, batchSize,
            consumer);
    }

    protected SqlSessionFactory getSqlSessionFactory() {
        if (this.sqlSessionFactory == null) {
            MapperProxyMetadata mapperProxyMetadata = MybatisUtils.getMapperProxy(this.getBaseMapper());
            this.sqlSessionFactory = MybatisUtils.getSqlSessionFactory(mapperProxyMetadata.getSqlSession());
        }
        return this.sqlSessionFactory;
    }

    @Override
    public M getBaseMapper() {
        return this.baseMapper;
    }

}
