package com.example.demo.weixin.service;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.weixin.api.JsapiService;
import com.example.demo.weixin.api.TicketService;
import com.example.demo.weixin.api.bo.Jsapi;
import com.example.demo.framework.util.EncryptUtil;

/**
 * @author JiakunXu
 */
@Service
public class JsapiServiceImpl implements JsapiService {

    private static final Logger logger = LoggerFactory.getLogger(JsapiServiceImpl.class);

    @Autowired
    private TicketService       ticketService;

    @Value("${weixin.app.id}")
    private String              appId;

    @Value("${weixin.app.secret}")
    private String              appSecret;

    @Override
    public Jsapi getJsapi(String url) {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("url cannot be null.");
        }

        String ticket = ticketService.getTicket(appId, appSecret);
        String nonceStr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature;

        StringBuilder sb = new StringBuilder();
        sb.append("jsapi_ticket=").append(ticket).append("&noncestr=").append(nonceStr)
            .append("&timestamp=").append(timestamp).append("&url=").append(url.trim());

        try {
            signature = EncryptUtil.encryptSha(sb.toString());
        } catch (IOException e) {
            logger.error("encryptSHA", e);

            throw new RuntimeException(e.getMessage());
        }

        Jsapi jsapi = new Jsapi();
        jsapi.setAppId(appId);
        jsapi.setNonceStr(nonceStr);
        jsapi.setTimestamp(timestamp);
        jsapi.setSignature(signature);

        return jsapi;
    }

}
