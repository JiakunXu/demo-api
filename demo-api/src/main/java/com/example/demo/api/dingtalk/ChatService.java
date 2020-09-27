package com.example.demo.api.dingtalk;

import com.example.demo.api.dingtalk.ao.msg.Msg;

/**
 * @author JiakunXu
 */
public interface ChatService {

    void send(String accessToken, String chatId, Msg msg);

}
