package com.example.demo.dingtalk.api;

/**
 * @author JiakunXu
 */
public interface CorpConversationService {

    String HTTPS_SEND_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";

    /**
     *
     * @param accessToken
     * @param agentId
     * @param useridList
     * @param title
     * @param text
     */
    void send(String accessToken, Long agentId, String useridList, String title, String text);

}
