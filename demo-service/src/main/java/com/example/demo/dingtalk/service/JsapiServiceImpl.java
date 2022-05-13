package com.example.demo.dingtalk.service;

import com.example.demo.dingtalk.api.AgentService;
import com.example.demo.dingtalk.api.JsapiService;
import com.example.demo.dingtalk.api.TicketService;
import com.example.demo.dingtalk.api.bo.js.Jsapi;
import com.example.demo.dingtalk.api.bo.agent.Agent;
import com.example.demo.framework.util.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * @author JiakunXu
 */
@Service("jsapiService0")
public class JsapiServiceImpl implements JsapiService {

    private static final Logger logger = LoggerFactory.getLogger(JsapiServiceImpl.class);

    @Autowired
    private AgentService        agentService;

    @Autowired
    private TicketService       ticketService;

    @Override
    public Jsapi getJsapi(String agentId, String url) {
        if (StringUtils.isBlank(agentId)) {
            throw new RuntimeException("agentId cannot be null.");
        }

        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("url cannot be null.");
        }

        Agent agent = agentService.getAgent(agentId);

        if (agent == null) {
            throw new RuntimeException("agent is null.");
        }

        String ticket = ticketService.getTicket(agent.getAppKey(), agent.getAppSecret());
        String nonceStr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature;

        StringBuilder sb = new StringBuilder();
        sb.append("jsapi_ticket=").append(ticket).append("&noncestr=").append(nonceStr)
            .append("&timestamp=").append(timestamp).append("&url=").append(url);

        try {
            signature = EncryptUtil.encryptSha(sb.toString());
        } catch (IOException e) {
            logger.error("encryptSHA", e);

            throw new RuntimeException(e.getMessage());
        }

        Jsapi jsapi = new Jsapi();
        jsapi.setAgentId(agentId);
        jsapi.setCorpId(agent.getCorpId());
        jsapi.setTimestamp(timestamp);
        jsapi.setNonceStr(nonceStr);
        jsapi.setSignature(signature);

        return jsapi;
    }

}
