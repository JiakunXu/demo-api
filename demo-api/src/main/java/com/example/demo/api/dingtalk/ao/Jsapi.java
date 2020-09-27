package com.example.demo.api.dingtalk.ao;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class Jsapi implements Serializable {

    private static final long serialVersionUID = -6957345637848956947L;

    /**
     * 微应用ID.
     */
    private String            agentId;

    /**
     * 企业ID.
     */
    private String            corpId;

    /**
     * 生成签名的时间戳.
     */
    @JSONField(name = "timeStamp")
    private String            timestamp;

    /**
     * 生成签名的随机串.
     */
    private String            nonceStr;

    /**
     * JSAPI签名.
     */
    private String            signature;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
