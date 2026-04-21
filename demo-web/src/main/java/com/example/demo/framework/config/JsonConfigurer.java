package com.example.demo.framework.config;

import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JiakunXu
 */
@Configuration
public class JsonConfigurer implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // config.setWriterFeatures(JSONWriter.Feature.WriteNullListAsEmpty);
        SimplePropertyPreFilter writerFilter = new SimplePropertyPreFilter();
        writerFilter.getExcludes().add("codes");
        config.setWriterFilters(writerFilter);

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setFastJsonConfig(config);

        builder.withJsonConverter(converter);
    }

}