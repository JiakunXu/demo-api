package com.example.demo.dingtalk.service;

import com.example.demo.dingtalk.api.DingtalkNotifyService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DingtalkNotifyServiceImpl implements DingtalkNotifyService {

    @Override
    public Map<String, String> notify(String signature, String timeStamp, String nonce,
                                      String encrypt) {
        return Map.of();
    }

}
