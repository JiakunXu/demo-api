package com.example.demo.dingtalk.api;

import java.util.Map;

public interface DingtalkService {

    Map<String, String> callback(String signature, String timeStamp, String nonce, String encrypt);

}
