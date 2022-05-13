package com.example.demo.dingtalk.api;

import java.util.Map;

/**
 * @author JiakunXu
 */
public interface EventService {

    /**
     *
     * @param agentId
     * @param signature 消息体签名
     * @param timeStamp 时间戳
     * @param nonce 随机字符串
     * @param encrypt 加密的推送事件信息
     * @return
     */
    Map<String, String> callback(String agentId, String signature, String timeStamp, String nonce,
                                 String encrypt);

}
