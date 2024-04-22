package com.example.demo.dingtalk.service;

import com.example.demo.dingtalk.api.NotifyService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("com.example.demo.dingtalk.service.notifyService")
public class NotifyServiceImpl implements NotifyService {

    @Override
    public Map<String, String> notify(String signature, String timeStamp, String nonce,
                                      String encrypt) {
        return Map.of();
    }

}
