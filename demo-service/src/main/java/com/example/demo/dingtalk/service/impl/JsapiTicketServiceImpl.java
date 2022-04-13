package com.example.demo.dingtalk.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.example.demo.api.dingtalk.JsapiTicketService;
import com.example.demo.api.dingtalk.ao.JsapiTicket;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("jsapiTicketService0")
public class JsapiTicketServiceImpl implements JsapiTicketService {

    private static final Logger logger = LoggerFactory.getLogger(JsapiTicketServiceImpl.class);

    @Override
    public JsapiTicket getJsapiTicket(String accessToken) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        DefaultDingTalkClient client = new DefaultDingTalkClient(
            JsapiTicketService.HTTPS_TICKET_URL);

        OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
        req.setTopHttpMethod("GET");

        OapiGetJsapiTicketResponse execute = null;

        try {
            execute = client.execute(req, accessToken);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(req), e);
            throw new RuntimeException("execute", e);
        }

        if (execute == null) {
            throw new RuntimeException("response is null.");
        }

        String errCode = execute.getErrorCode();

        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(execute.getErrmsg());
        }

        JsapiTicket jsapiTicket = new JsapiTicket();
        jsapiTicket.setTicket(execute.getTicket());
        jsapiTicket.setExpiresIn(execute.getExpiresIn());

        return jsapiTicket;
    }

}
