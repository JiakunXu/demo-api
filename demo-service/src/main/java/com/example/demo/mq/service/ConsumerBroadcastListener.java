package com.example.demo.mq.service;

import com.example.demo.socket.api.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JiakunXu
 *
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "topic", selectorExpression = "web.socket", messageModel = MessageModel.BROADCASTING, enableMsgTrace = true)
public class ConsumerBroadcastListener implements RocketMQListener<MessageExt> {

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void onMessage(MessageExt message) {
        String tag = message.getTags();

        if ("web.socket".equals(tag)) {
            try {
                String tunnelId = message.getKeys();

                webSocketService.sendMessage(tunnelId, new String(message.getBody()));
            } catch (Exception e) {
                log.error("{}", message, e);
            }
        }
    }

}
