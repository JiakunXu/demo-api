package com.example.demo.weixin.manager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.weixin.api.JsapiTicketService;
import com.example.demo.weixin.api.bo.JsapiTicket;
import com.example.demo.framework.util.HttpUtil;

/**
 * @author JiakunXu
 */
@Service
public class JsapiTicketServiceImpl implements JsapiTicketService {

    private static final Logger logger = LoggerFactory.getLogger(JsapiTicketServiceImpl.class);

    @Override
    public JsapiTicket getJsapiTicket(String accessToken) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        JsapiTicket jsapiTicket = null;

        try {
            jsapiTicket = JSON.parseObject(HttpUtil.get(
                JsapiTicketService.HTTPS_TICKET_URL.replace("$ACCESS_TOKEN$", accessToken.trim())),
                JsapiTicket.class);
        } catch (Exception e) {
            logger.error(accessToken, e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (jsapiTicket == null) {
            throw new RuntimeException("jsapi_ticket is null.");
        }

        if (jsapiTicket.getErrCode() != 0) {
            throw new RuntimeException(jsapiTicket.getErrMsg());
        }

        String ticket = jsapiTicket.getTicket();

        if (StringUtils.isBlank(ticket)) {
            throw new RuntimeException("ticket is blank.");
        }

        return jsapiTicket;
    }
}
