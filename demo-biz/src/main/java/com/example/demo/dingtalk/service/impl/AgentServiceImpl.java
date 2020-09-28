package com.example.demo.dingtalk.service.impl;

import com.example.demo.api.dingtalk.AgentService;
import com.example.demo.api.dingtalk.bo.Agent;
import com.example.demo.dingtalk.dao.AgentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class AgentServiceImpl implements AgentService {

    private static final Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    private AgentDao            agentDao;

    @Override
    public Agent getAgent(String agentId) {
        return null;
    }

}
