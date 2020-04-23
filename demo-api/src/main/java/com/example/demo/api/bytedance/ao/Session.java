package com.example.demo.api.bytedance.ao;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author JiakunXu
 */
public class Session extends BaseResult {

    private static final long serialVersionUID = -4488739136579434757L;

    /**
     * 会话密钥，如果请求时有 code 参数才会返回.
     */
    @JSONField(name = "session_key")
    private String            sessionKey;

    /**
     * 用户在当前小程序的 ID，如果请求时有 code 参数才会返回.
     */
    @JSONField(name = "openid")
    private String            openId;

    /**
     * 匿名用户在当前小程序的 ID，如果请求时有 anonymous_code 参数才会返回.
     */
    @JSONField(name = "anonymous_openid")
    private String            anonymousOpenId;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAnonymousOpenId() {
        return anonymousOpenId;
    }

    public void setAnonymousOpenId(String anonymousOpenId) {
        this.anonymousOpenId = anonymousOpenId;
    }

}
