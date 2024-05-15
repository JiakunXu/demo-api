package com.example.demo.dingtalk.api;

import java.util.Map;

public interface MessageService {

    String HTTPS_SEND_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";

    Map<String, String> notify(String signature, String timeStamp, String nonce, String encrypt);

    void send(String accessToken, Long agentId, String useridList, String mediaId);

    void send(String accessToken, Long agentId, String useridList, String title, String text);

}
