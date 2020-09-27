package com.example.demo.dingtalk.service.impl;

import com.example.demo.api.cache.RedisService;
import com.example.demo.api.dingtalk.JsapiTicketService;
import com.example.demo.api.dingtalk.TicketService;
import com.example.demo.api.dingtalk.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("ticketService0")
public class TicketServiceImpl implements TicketService {

    private static final Logger          logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private RedisService<String, String> redisService;

    @Autowired
    private TokenService                 tokenService;

    @Autowired
    private JsapiTicketService           jsapiTicketService;

    @Override
    public String getTicket(String appKey, String appSecret) throws RuntimeException {
        return null;
    }
}
