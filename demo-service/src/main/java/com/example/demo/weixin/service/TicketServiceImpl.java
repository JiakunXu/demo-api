package com.example.demo.weixin.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.weixin.api.JsapiTicketService;
import com.example.demo.weixin.api.TicketService;
import com.example.demo.weixin.api.TokenService;
import com.example.demo.weixin.api.bo.js.JsapiTicket;
import com.example.demo.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private JsapiTicketService           jsapiTicketService;

    @Autowired
    private RedisService<String, String> redisService;

    @Autowired
    private TokenService                 tokenService;

    @Override
    public String getTicket(String appId, String appSecret) throws RuntimeException {
        String key = appId + "&" + appSecret;

        String ticket = null;

        try {
            ticket = redisService.get(RedisService.CACHE_KEY_WX_TICKET + key);
        } catch (ServiceException e) {
            log.error(RedisService.CACHE_KEY_WX_TICKET + key, e);
        }

        if (StringUtils.isNotBlank(ticket)) {
            return ticket;
        }

        JsapiTicket jsapiTicket = jsapiTicketService
            .getJsapiTicket(tokenService.getToken("client_credential", appId, appSecret));

        ticket = jsapiTicket.getTicket();

        try {
            redisService.set(RedisService.CACHE_KEY_WX_TICKET + key, ticket,
                jsapiTicket.getExpiresIn() - 5 * 60);
        } catch (ServiceException e) {
            log.error(RedisService.CACHE_KEY_WX_TICKET + key, e);
        }

        return ticket;
    }

}
