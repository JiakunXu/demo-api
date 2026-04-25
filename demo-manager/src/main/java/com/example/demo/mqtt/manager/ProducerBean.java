package com.example.demo.mqtt.manager;

import com.alibaba.mqtt.server.ServerProducer;
import com.alibaba.mqtt.server.config.ChannelConfig;
import com.alibaba.mqtt.server.config.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerBean {

    @Value("${aliyun.mqtt.domain:}")
    private String domain;

    @Value("${aliyun.mqtt.port:}")
    private int    port;

    @Value("${aliyun.mqtt.instanceId:}")
    private String instanceId;

    @Value("${aliyun.accessKey.id:}")
    private String accessKey;

    @Value("${aliyun.accessKey.secret:}")
    private String secretKey;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public ServerProducer serverProducer() {
        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.setDomain(domain);
        channelConfig.setPort(port);
        channelConfig.setInstanceId(instanceId);
        channelConfig.setAccessKey(accessKey);
        channelConfig.setSecretKey(secretKey);

        return new ServerProducer(channelConfig, new ProducerConfig());
    }

}
