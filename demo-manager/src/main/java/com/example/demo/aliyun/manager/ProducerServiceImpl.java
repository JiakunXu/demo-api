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
    public String syncSend(String topic, String tags, byte[] body, String keys) {
        try {
            return rocketMQTemplate
                .syncSend(topic + ":" + tags,
                    MessageBuilder.withPayload(body).setHeader(RocketMQHeaders.KEYS, keys).build())
                .getMsgId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String syncSend(String topic, String tags, byte[] body, String keys, long delayTime) {
        try {
            return rocketMQTemplate.syncSendDelayTimeSeconds(topic + ":" + tags,
                MessageBuilder.withPayload(body).setHeader(RocketMQHeaders.KEYS, keys).build(),
                delayTime).getMsgId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void send(String topic, String tags, byte[] body, String keys) {
        try {
            rocketMQTemplate.send(topic + ":" + tags,
                MessageBuilder.withPayload(body).setHeader(RocketMQHeaders.KEYS, keys).build());
        } catch (Exception e) {
            logger.error("send", e);
        }
    }

}
