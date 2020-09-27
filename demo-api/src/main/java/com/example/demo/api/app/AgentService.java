package com.example.demo.api.app;

import com.example.demo.api.app.bo.Agent;

/**
 * @author JiakunXu
 */
public interface AgentService {

    /**
     * 
     * @param agentId
     * @return
     */
    Agent getAgent(String agentId);

}
