package com.example.demo.app.service.impl;

import com.example.demo.api.app.AgentService;
import com.example.demo.api.app.bo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class AgentServiceImpl implements AgentService {

    private static final Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Override
    public Agent getAgent(String agentId) {
        return null;
    }

}
