package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.bo.message.Result;
import com.example.demo.bytedance.api.bo.message.Message;

/**
 * @author JiakunXu
 */
public interface MessageService {

    String HTTPS_POST_URL = "https://developer.toutiao.com/api/apps/message/custom/send?access_token=";

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
    Message notify(String signature, String timestamp, String nonce,
                   String data) throws RuntimeException;

    /**
     *
     * @param accessToken
     * @param openId
     * @param content
     * @throws RuntimeException
     */
    Result send(String accessToken, String openId, String content) throws RuntimeException;

}
