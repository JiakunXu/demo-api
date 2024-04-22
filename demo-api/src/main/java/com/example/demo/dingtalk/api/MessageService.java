package com.example.demo.dingtalk.api;

import java.util.Map;

public interface MessageService {

    Map<String, String> notify(String signature, String timeStamp, String nonce, String encrypt);

}
