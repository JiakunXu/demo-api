package com.example.demo.aliyun.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.*;
import com.example.demo.api.aliyun.ProducerService;

/**
 * @author JiakunXu
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Autowired
    private Producer            producer;

    @Override
    public String sendAsync(String topic, String tags, byte[] body, String key) {
        return sendAsync(topic, tags, body, key, Long.parseLong("0"));
    }

    @Override
    public String sendAsync(String topic, String tags, byte[] body, String key, long timeStamp) {
        Message message = new Message(topic, tags, body);
        message.setKey(key);

        if (timeStamp > 0) {
            message.setStartDeliverTime(timeStamp);
        }

        try {
            producer.sendAsync(message, new SendCallback() {
                @Override
                public void onSuccess(final SendResult sendResult) {
                }

                @Override
                public void onException(OnExceptionContext context) {
                    logger.error("sendAsync", context.getException());

                    producer.sendOneway(message);
                }
            });
        } catch (Exception e) {
            logger.error("sendAsync", e.getMessage());
        }

        return message.getMsgID();
    }

}
