package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.JsapiTicketService;
import com.example.demo.weixin.api.bo.js.JsapiTicket;
import com.example.demo.framework.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class JsapiTicketServiceImpl implements JsapiTicketService {

    @Override
    public JsapiTicket getJsapiTicket(String accessToken) throws RuntimeException {
        JsapiTicket jsapiTicket;

        try {
            jsapiTicket = JSON.parseObject(
                HttpUtil.get(MessageFormat.format(HTTPS_GET_URL, accessToken)), JsapiTicket.class);
        } catch (Exception e) {
            log.error("{}", accessToken, e);
            throw new RuntimeException(e.getMessage(), e);
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
