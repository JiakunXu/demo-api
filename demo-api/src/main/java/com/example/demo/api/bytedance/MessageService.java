package com.example.demo.api.bytedance;

import com.example.demo.api.bytedance.ao.SendResult;
import com.example.demo.api.bytedance.bo.Message;

/**
 * @author JiakunXu
 */
public interface MessageService {

    String HTTPS_MESSAGE_URL = "https://developer.toutiao.com/api/apps/message/custom/send?access_token=";

    /**
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     * @throws RuntimeException
     */
    String verify(String signature, String timestamp, String nonce,
                  String echostr) throws RuntimeException;

    /**
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param data
     * @return
     * @throws RuntimeException
     */
    Message callback(String signature, String timestamp, String nonce,
                     String data) throws RuntimeException;

    /**
     *
     * @param accessToken
     * @param openId
     * @param content
     * @throws RuntimeException
     */
    SendResult send(String accessToken, String openId, String content) throws RuntimeException;
}
