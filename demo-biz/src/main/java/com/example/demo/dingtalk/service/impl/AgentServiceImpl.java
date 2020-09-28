package com.example.demo.dingtalk.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.dingtalk.AgentService;
import com.example.demo.api.dingtalk.bo.Agent;
import com.example.demo.dingtalk.dao.AgentDao;
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
    private AgentDao            agentDao;

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
            return agentDao.getAgent(agent);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(agent), e);
        }

        return null;
    }

}
