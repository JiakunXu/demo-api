package com.example.demo.weixin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.cache.MemcachedService;
import com.example.demo.api.weixin.JsapiTicketService;
import com.example.demo.api.weixin.TicketService;
import com.example.demo.api.weixin.TokenService;
import com.example.demo.api.weixin.ao.JsapiTicket;
import com.example.demo.framework.exception.ServiceException;

/**
 * @author JiakunXu
 */
@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger   logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private MemcachedService memcachedService;

    @Autowired
    private TokenService          tokenService;

    @Autowired
    private JsapiTicketService    jsapiTicketService;

    @Override
    public String getTicket(String appId, String appSecret) throws RuntimeException {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appId 不能为空.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("appSecret 不能为空.");
        }

        String ticket = null;
        String key = appId.trim() + "&" + appSecret.trim();

        try {
            ticket = (String) memcachedService
                .get(MemcachedService.CACHE_KEY_WX_TICKET + key);
        } catch (ServiceException e) {
            logger.error(MemcachedService.CACHE_KEY_WX_TICKET + key, e);
        }

        if (StringUtils.isNotBlank(ticket)) {
            return ticket;
        }

        String token = tokenService.getToken("client_credential", appId, appSecret);
        JsapiTicket jsapiTicket = jsapiTicketService.getJsapiTicket(token);
        ticket = jsapiTicket.getTicket();

        try {
            memcachedService.set(MemcachedService.CACHE_KEY_WX_TICKET + key, ticket,
                jsapiTicket.getExpiresIn());
        } catch (ServiceException e) {
            logger.error(MemcachedService.CACHE_KEY_WX_TICKET + key, e);
        }

        return ticket;
    }
}
