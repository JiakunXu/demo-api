package com.example.demo.weixin.service;

import com.example.demo.weixin.api.NotifyService;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("com.example.demo.weixin.service.notifyService")
public class NotifyServiceImpl implements NotifyService {

    @Override
    public String verify(String signature, String timestamp, String nonce, String echoStr) {
        return "";
    }

    @Override
    public String notify(String signature, String timestamp, String nonce, String data) {
        return "";
    }

}
