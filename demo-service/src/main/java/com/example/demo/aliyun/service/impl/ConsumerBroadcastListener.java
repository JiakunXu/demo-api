package com.example.demo.aliyun.service.impl;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.example.demo.api.socket.WebSocketService;
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
public class ConsumerBroadcastListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerBroadcastListener.class);

    @Autowired
    private WebSocketService    webSocketService;

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        String tag = message.getTag();

        if ("web.socket".equals(tag)) {
            try {
                String tunnelId = message.getKey();

                webSocketService.sendMessage(tunnelId, new String(message.getBody()));
            } catch (Exception e) {
                logger.error("web.socket", e);
            }

            return Action.CommitMessage;
        }

        return Action.ReconsumeLater;
    }

}
