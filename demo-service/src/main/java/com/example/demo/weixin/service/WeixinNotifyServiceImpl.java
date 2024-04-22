package com.example.demo.weixin.service;

import com.example.demo.weixin.api.WeixinNotifyService;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class WeixinNotifyServiceImpl implements WeixinNotifyService {

    @Override
    public String verify(String signature, String timestamp, String nonce, String echoStr) {
        return "";
    }

    @Override
    public String notify(String signature, String timestamp, String nonce, String data) {
        return "";
    }

}
