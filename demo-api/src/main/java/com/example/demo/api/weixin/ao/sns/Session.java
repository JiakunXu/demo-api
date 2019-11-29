package com.example.demo.api.weixin.ao.sns;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.api.weixin.ao.BaseResult;

/**
 * @author JiakunXu
 */
public class Session extends BaseResult {

    private static final long serialVersionUID = 4107709675927719697L;

    /**
     * 用户唯一标识.
     */
    @JSONField(name = "openid")
    private String            openId;

    /**
     * 会话密钥.
     */
    @JSONField(name = "session_key")
    private String            sessionKey;

    /**
     * 用户在开放平台的唯一标识符.
     */
    @JSONField(name = "unionid")
    private String            unionId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
