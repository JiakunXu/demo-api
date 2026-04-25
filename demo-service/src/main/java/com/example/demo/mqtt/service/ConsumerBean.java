package com.example.demo.mqtt.service;

import com.alibaba.mqtt.server.ServerConsumer;
import com.alibaba.mqtt.server.config.ChannelConfig;
import com.alibaba.mqtt.server.config.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class ConsumerBean {

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

    @Value("${aliyun.mqtt.topic:}")
    private String topic;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public ServerConsumer consumer(ConsumerListener consumerListener) throws IOException,
                                                                      TimeoutException {
        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.setDomain(domain);
        channelConfig.setPort(port);
        channelConfig.setInstanceId(instanceId);
        channelConfig.setAccessKey(accessKey);
        channelConfig.setSecretKey(secretKey);

        ServerConsumer serverConsumer = new ServerConsumer(channelConfig, new ConsumerConfig());
        serverConsumer.subscribeTopic(topic, consumerListener);

        return serverConsumer;
    }

}
