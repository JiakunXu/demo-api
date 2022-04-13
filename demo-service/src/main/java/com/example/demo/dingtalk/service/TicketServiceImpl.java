package com.example.demo.dingtalk.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.dingtalk.api.JsapiTicketService;
import com.example.demo.dingtalk.api.TicketService;
import com.example.demo.dingtalk.api.TokenService;
import com.example.demo.dingtalk.api.ao.JsapiTicket;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isBlank(appKey)) {
            throw new RuntimeException("appkey is null.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("appsecret is null.");
        }

        String ticket = null;

        String key = appKey.trim() + "&" + appSecret.trim();

        try {
            ticket = redisService.get(RedisService.CACHE_KEY_DD_TICKET + key);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DD_TICKET + key, e);
        }

        if (StringUtils.isNotBlank(ticket)) {
            return ticket;
        }

        JsapiTicket jsapiTicket = jsapiTicketService
            .getJsapiTicket(tokenService.getToken(appKey, appSecret));

        ticket = jsapiTicket.getTicket();

        try {
            redisService.set(RedisService.CACHE_KEY_DD_TICKET + key, ticket,
                jsapiTicket.getExpiresIn());
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_DD_TICKET + key, e);
        }

        return ticket;
    }
}
