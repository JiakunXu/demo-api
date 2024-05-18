package com.example.demo.bytedance.api.bo.session;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.bytedance.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Session extends BaseResult {

    @Serial
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

}
