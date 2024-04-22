package com.example.demo.bytedance.service;

import com.example.demo.bytedance.api.BytedanceNotifyService;
import com.example.demo.bytedance.api.bo.message.Message;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class BytedanceNotifyServiceImpl implements BytedanceNotifyService {

    @Override
    public String verify(String signature, String timestamp, String nonce, String echostr) {
        return "";
    }

    @Override
    public Message notify(String signature, String timestamp, String nonce, String data) {
        return null;
    }

}
