package com.example.demo.dingtalk.service;

import com.example.demo.dingtalk.api.AgentService;
import com.example.demo.dingtalk.api.bo.agent.Agent;
import com.example.demo.dingtalk.dao.dataobject.AgentDO;
import com.example.demo.dingtalk.dao.mapper.AgentMapper;
import com.example.demo.framework.util.BeanUtil;
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
    private AgentMapper         agentMapper;

    @Override
    public Agent getAgent(String agentId) {
        if (StringUtils.isBlank(agentId)) {
            return null;
        }

        AgentDO agentDO = new AgentDO();
        agentDO.setAgentId(agentId);

        return BeanUtil.copy(get(agentDO), Agent::new);
    }

    private AgentDO get(AgentDO agentDO) {
        try {
            return agentMapper.get(agentDO);
        } catch (Exception e) {
            logger.error(agentDO.toString(), e);
        }

        return null;
    }

}
