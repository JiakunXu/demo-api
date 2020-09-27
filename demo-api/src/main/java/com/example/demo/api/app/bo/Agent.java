package com.example.demo.api.app.bo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author JiakunXu
 */
public class Agent implements Serializable {

    private static final long serialVersionUID = 2872975505231242077L;

    private BigInteger        id;

    private String            corpId;

    private String            agentId;

    private String            appKey;

    private String            appSecret;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
