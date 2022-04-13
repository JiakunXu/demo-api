package com.example.demo.aliyun.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.chat.api.ChatService;
import com.example.demo.chat.api.bo.Chat;
import com.example.demo.chat.api.bo.ChatDetail;
import com.example.demo.subscribe.api.SubscribeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ConsumerClusterListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerClusterListener.class);

    @Autowired
    private ChatService         chatService;

    @Autowired
    private SubscribeService    subscribeService;

    @Override
    public Action consume(Message message, ConsumeContext context) {
        String tag = message.getTag();

        if ("chat.message".equals(tag)) {
            try {
                String sceneId = message.getKey();
                ChatDetail chatDetail = JSON.parseObject(message.getBody(), ChatDetail.class);

                subscribeService.sendMessage("app", sceneId,
                    new com.example.demo.socket.api.ao.Message(
                        "chat." + chatDetail.getFriendId() + ".message",
                        new String(message.getBody())));

                return Action.CommitMessage;
            } catch (Exception e) {
                logger.error("chat.message", e);
            }
        }

        if ("chat.update".equals(tag)) {
            try {
                String chatId = message.getKey();
                Chat chat = JSON.parseObject(message.getBody(), Chat.class);

                chatService.saveOrUpdate(chat.getUserId(), chat.getFriendId(), chat);

                return Action.CommitMessage;
            } catch (Exception e) {
                logger.error("chat.update", e);
            }
        }

        return Action.ReconsumeLater;
    }

}
