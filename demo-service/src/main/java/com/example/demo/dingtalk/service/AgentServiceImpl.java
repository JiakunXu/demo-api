package com.example.demo.dingtalk.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.dingtalk.api.AgentService;
import com.example.demo.dingtalk.api.bo.Agent;
import com.example.demo.dingtalk.dao.mapper.AgentMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("agentService0")
public class AgentServiceImpl implements AgentService {

    private static final Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Autowired
    private AgentMapper agentMapper;

    @Override
    public Agent getAgent(String agentId) {
        if (StringUtils.isBlank(agentId)) {
            return null;
        }

        Agent agent = new Agent();
        agent.setAgentId(agentId);

        return getAgent(agent);
    }

    private Agent getAgent(Agent agent) {
        try {
            return agentMapper.getAgent(agent);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(agent), e);
        }

        return null;
    }

}
