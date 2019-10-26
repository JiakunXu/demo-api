package com.example.demo.aliyun.service.impl;

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
public class ConsumerListener implements MessageListener {

    @Override
    public Action consume(Message message, ConsumeContext context) {
        String tag = message.getTag();

        if ("tag".equals(tag)) {
            return Action.CommitMessage;
        }

        return Action.ReconsumeLater;
    }

}
