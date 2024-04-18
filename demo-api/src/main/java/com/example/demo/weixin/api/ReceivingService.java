package com.example.demo.weixin.api;

/**
 * @author JiakunXu
 */
public interface ReceivingService {

    /**
     * 
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echoStr 随机字符串
     * @return
     * @throws RuntimeException
     */
    String verify(String signature, String timestamp, String nonce,
                  String echoStr) throws RuntimeException;

    /**
     * 
     * @param signature
     * @param timestamp
     * @param nonce
     * @param data
     * @return
     * @throws RuntimeException
     */
    String notify(String signature, String timestamp, String nonce,
                  String data) throws RuntimeException;

}
