package com.example.demo.aliyun.manager;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.aliyun.api.ProducerService;

/**
 * @author JiakunXu
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Autowired
    private Producer            producer;

    @Override
    public String send(String topic, String tags, byte[] body, String key) {
        return send(topic, tags, body, key, Long.parseLong("0"));
    }

    @Override
    public String send(String topic, String tags, byte[] body, String key, long timeStamp) {
        Message message = new Message(topic, tags, body);
        message.setKey(key);

        if (timeStamp > 0) {
            message.setStartDeliverTime(timeStamp);
        }

        try {
            producer.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return message.getMsgID();
    }

    @Override
    public String sendOneway(String topic, String tags, byte[] body, String key) {
        Message message = new Message(topic, tags, body);
        message.setKey(key);

        try {
            producer.sendOneway(message);
        } catch (Exception e) {
            logger.error("sendOneway", e);
        }

        return message.getMsgID();
    }

}
