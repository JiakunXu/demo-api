package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.bo.message.Message;

public interface BytedanceNotifyService {

    String verify(String signature, String timestamp, String nonce, String echostr);

    Message notify(String signature, String timestamp, String nonce, String data);

}
