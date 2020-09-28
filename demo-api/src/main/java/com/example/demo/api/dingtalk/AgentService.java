package com.example.demo.api.dingtalk;

import com.example.demo.api.dingtalk.bo.Agent;

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
