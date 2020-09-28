package com.example.demo.api.dingtalk;

import com.example.demo.api.dingtalk.ao.msg.Msg;

/**
 * @author JiakunXu
 */
public interface ChatService {

    String HTTPS_CHAT_URL = "https://oapi.dingtalk.com/chat/send";

    void send(String accessToken, String chatId, Msg msg);

}
