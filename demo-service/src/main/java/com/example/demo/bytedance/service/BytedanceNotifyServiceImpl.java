package com.example.demo.bytedance.service;

import com.example.demo.bytedance.api.BytedanceNotifyService;
import com.example.demo.bytedance.api.bo.message.Message;
import com.example.demo.bytedance.dao.mapper.BytedanceNotifyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class BytedanceNotifyServiceImpl implements BytedanceNotifyService {

    private static final Logger   logger = LoggerFactory
        .getLogger(BytedanceNotifyServiceImpl.class);

    @Autowired
    private BytedanceNotifyMapper bytedanceNotifyMapper;

    @Override
    public String verify(String signature, String timestamp, String nonce, String echostr) {
        return "";
    }

    @Override
    public Message notify(String signature, String timestamp, String nonce, String data) {
        return null;
    }

}
