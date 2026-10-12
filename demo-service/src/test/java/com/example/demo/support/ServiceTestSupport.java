package com.example.demo.support;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.test.util.ReflectionTestUtils;

public final class ServiceTestSupport {
    private ServiceTestSupport() {
    }

    public static void mapper(Object service, Object mapper, Class<?> entity) {
        ReflectionTestUtils.setField(service, "baseMapper", mapper);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), "unit-test"), entity);
    }
}
