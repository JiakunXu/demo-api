package com.example.demo.dingtalk.api;

import java.util.Map;

public interface MessageService {

    String HTTPS_SEND_URL   = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";

    String HTTPS_RECALL_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/recall";

    Map<String, String> notify(String signature, String timeStamp, String nonce, String encrypt);

    Long send(String accessToken, Long agentId, String useridList, String mediaId);

    Long send(String accessToken, Long agentId, String useridList, String title, String text);

    void recall(String accessToken, Long agentId, Long msgTaskId);

}
