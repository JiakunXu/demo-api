package com.example.demo.mq.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.chat.api.ChatService;
import com.example.demo.chat.api.bo.Chat;
import com.example.demo.chat.api.bo.ChatDetail;
import com.example.demo.subscribe.api.SubscribeService;
import org.apache.rocketmq.common.message.MessageExt;
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
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "topic", selectorExpression = "chat.message||chat.update", enableMsgTrace = true)
public class ConsumerClusterListener implements RocketMQListener<MessageExt> {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerClusterListener.class);

    @Autowired
    private ChatService         chatService;

    @Autowired
    private SubscribeService    subscribeService;

    @Override
    public void onMessage(MessageExt message) {
        String tag = message.getTags();

        if ("chat.message".equals(tag)) {
            String sceneId = message.getKeys();
            ChatDetail chatDetail = JSON.parseObject(message.getBody(), ChatDetail.class);

            subscribeService.sendMessage("app", sceneId, new com.example.demo.socket.api.bo.Message(
                "chat." + chatDetail.getFriendId() + ".message", new String(message.getBody())));
        }

        if ("chat.update".equals(tag)) {
            String chatId = message.getKeys();
            Chat chat = JSON.parseObject(message.getBody(), Chat.class);

            chatService.saveOrUpdate(chat.getUserId(), chat.getFriendId(), chat);
        }
    }

}
