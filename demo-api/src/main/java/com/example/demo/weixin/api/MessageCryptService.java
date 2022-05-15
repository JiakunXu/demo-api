package com.example.demo.weixin.api;

/**
 * @author JiakunXu
 */
public interface MessageCryptService {

    /**
     * 
     * @param token 申请公众号第三方平台时填写的接收消息的校验 token
     * @param encodingAesKey 申请公众号第三方平台时填写的接收消息的加密 symmetric_key
     * @param appId 公众号第三方平台的 appid
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     * @throws RuntimeException
     */
    String verify(String token, String encodingAesKey, String appId, String signature,
                  String timestamp, String nonce, String echostr) throws RuntimeException;

}
