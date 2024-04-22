package com.example.demo.dingtalk.service;

import com.example.demo.dingtalk.api.DingtalkNotifyService;
import com.example.demo.dingtalk.api.MessageService;
import com.example.demo.dingtalk.dao.mapper.DingtalkNotifyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DingtalkNotifyServiceImpl implements DingtalkNotifyService {

    private static final Logger  logger = LoggerFactory.getLogger(DingtalkNotifyServiceImpl.class);

    @Autowired
    private MessageService       messageService;

    @Autowired
    private DingtalkNotifyMapper dingtalkNotifyMapper;

    @Override
    public Map<String, String> notify(String signature, String timeStamp, String nonce,
                                      String encrypt) {
        return Map.of();
    }

}
