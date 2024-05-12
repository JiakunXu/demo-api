package com.example.demo.aliyun.service;

import com.example.demo.socket.api.WebSocketService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JiakunXu
 *
 */
@Service
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "topic", selectorExpression = "web.socket", messageModel = MessageModel.BROADCASTING, enableMsgTrace = true)
public class ConsumerBroadcastListener implements RocketMQListener<MessageExt> {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerBroadcastListener.class);

    @Autowired
    private WebSocketService    webSocketService;

    @Override
    public void onMessage(MessageExt message) {
        String tag = message.getTags();

        if ("web.socket".equals(tag)) {
            try {
                String tunnelId = message.getKeys();

                webSocketService.sendMessage(tunnelId, new String(message.getBody()));
            } catch (Exception e) {
                logger.error("web.socket", e);
            }
        }
    }

}
