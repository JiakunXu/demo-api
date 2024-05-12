package com.example.demo.aliyun.manager;

import com.example.demo.aliyun.api.ProducerService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Autowired
    private RocketMQTemplate    rocketMQTemplate;

    @Override
    public String syncSend(String topic, String tags, byte[] body, String key) {
        try {
            return rocketMQTemplate
                .syncSend(topic + ":" + tags,
                    MessageBuilder.withPayload(body).setHeader(RocketMQHeaders.KEYS, key).build())
                .getMsgId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String syncSend(String topic, String tags, byte[] body, String key, long delayTime) {
        try {
            return rocketMQTemplate.syncSendDelayTimeSeconds(topic + ":" + tags,
                MessageBuilder.withPayload(body).setHeader(RocketMQHeaders.KEYS, key).build(),
                delayTime).getMsgId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(String topic, String tags, byte[] body, String key) {
        try {
            rocketMQTemplate.send(topic + ":" + tags,
                MessageBuilder.withPayload(body).setHeader(RocketMQHeaders.KEYS, key).build());
        } catch (Exception e) {
            logger.error("send", e);
        }
    }

}
