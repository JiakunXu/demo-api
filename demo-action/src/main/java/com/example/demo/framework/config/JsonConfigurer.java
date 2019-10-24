package com.example.demo.framework.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @author JiakunXu
 */
@Configuration
public class JsonConfigurer {

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);

        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;

        return new HttpMessageConverters(converter);
    }

}
