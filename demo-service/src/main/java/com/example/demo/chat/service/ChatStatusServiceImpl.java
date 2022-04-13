package com.example.demo.chat.service;

import com.example.demo.chat.api.ChatStatusService;
import com.example.demo.subscribe.api.SubscribeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Service
public class ChatStatusServiceImpl implements ChatStatusService {

    @Autowired
    private SubscribeService subscribeService;

    @Override
    public String getStatus(BigInteger userId, String friendId) {
        if (userId == null || StringUtils.isBlank(friendId)) {
            return "busy";
        }

        if (subscribeService.countSubscribe(new BigInteger(friendId), "app", friendId) > 0) {
            return "online";
        }

        return "busy";
    }

}
