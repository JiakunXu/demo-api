package com.example.demo.dingtalk.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.aliyun.api.ProducerService;
import com.example.demo.dingtalk.api.AgentService;
import com.example.demo.dingtalk.api.EventService;
import com.example.demo.dingtalk.api.bo.Agent;
import com.example.demo.dingtalk.manager.DingCallbackCrypto;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private AgentService        agentService;

    @Autowired
    private ProducerService     producerService;

    @Value("${aliyun.ons.topic}")
    private String              topic;

    @Override
    public Map<String, String> callback(String agentId, String signature, String timeStamp,
                                        String nonce, String encrypt) {
        Agent agent = agentService.getAgent(agentId);

        if (agent == null) {
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息不存在");
        }

        try {
            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(agent.getToken(),
                agent.getAesKey(), agent.getAppKey());

            String decryptMsg = callbackCrypto.getDecryptMsg(signature, timeStamp, nonce, encrypt);

            String eventType = JSON.parseObject(decryptMsg).getString("EventType");

            // TODO
            if ("".equals(eventType)) {
                producerService.send(topic, eventType, JSON.toJSONBytes(decryptMsg), nonce);
            }

            return callbackCrypto.getEncryptedMap("success");
        } catch (DingCallbackCrypto.DingTalkEncryptException e) {
            logger.error("callback", e);
        }

        return null;
    }

}
