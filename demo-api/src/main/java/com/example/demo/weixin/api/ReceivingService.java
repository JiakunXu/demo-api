package com.example.demo.weixin.api;

/**
 * @author JiakunXu
 */
public interface ReceivingService {

    String verify(String signature, String timestamp, String nonce,
                  String echostr) throws RuntimeException;

}
