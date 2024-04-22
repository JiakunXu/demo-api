package com.example.demo.weixin.api;

public interface NotifyService {

    String verify(String signature, String timestamp, String nonce, String echoStr);

    String notify(String signature, String timestamp, String nonce, String data);

}
