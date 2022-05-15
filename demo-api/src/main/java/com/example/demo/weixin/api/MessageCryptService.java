package com.example.demo.weixin.api;

/**
 * @author JiakunXu
 */
public interface MessageCryptService {

    /**
     * 
     * @param token 申请公众号第三方平台时填写的接收消息的校验 token
     * @param encodingAesKey 申请公众号第三方平台时填写的接收消息的加密 symmetric_key
     * @param appid 公众号第三方平台的 appid
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echoStr
     * @return
     * @throws RuntimeException
     */
    String verify(String token, String encodingAesKey, String appid, String signature,
                  String timestamp, String nonce, String echoStr) throws RuntimeException;

    /**
     * 
     * @param token
     * @param encodingAesKey
     * @param appid
     * @param signature
     * @param timestamp
     * @param nonce
     * @param data
     * @return
     * @throws RuntimeException
     */
    String decrypt(String token, String encodingAesKey, String appid, String signature,
                   String timestamp, String nonce, String data) throws RuntimeException;

    /**
     * 
     * @param token
     * @param encodingAesKey
     * @param appid
     * @param data
     * @param timestamp
     * @param nonce
     * @return
     * @throws RuntimeException
     */
    String encrypt(String token, String encodingAesKey, String appid, String data, String timestamp,
                   String nonce) throws RuntimeException;

}
