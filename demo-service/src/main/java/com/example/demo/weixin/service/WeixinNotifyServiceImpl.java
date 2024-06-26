package com.example.demo.weixin.service;

import com.example.demo.weixin.api.MessageService;
import com.example.demo.weixin.api.WeixinNotifyService;
import com.example.demo.weixin.dao.mapper.WeixinNotifyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class WeixinNotifyServiceImpl implements WeixinNotifyService {

    private static final Logger logger = LoggerFactory.getLogger(WeixinNotifyServiceImpl.class);

    @Autowired
    private MessageService      messageService;

    @Autowired
    private WeixinNotifyMapper  weixinNotifyMapper;

    @Override
    public String verify(String signature, String timestamp, String nonce, String echoStr) {
        return messageService.verify(signature, timestamp, nonce, echoStr);
    }

    @Override
    public String notify(String signature, String timestamp, String nonce, String data) {
        messageService.notify(signature, timestamp, nonce, data);

        return "";
    }

}
