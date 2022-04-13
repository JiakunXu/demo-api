package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.Agent;

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
