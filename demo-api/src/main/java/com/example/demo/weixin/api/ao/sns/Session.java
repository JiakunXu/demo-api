package com.example.demo.weixin.api.ao.sns;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.weixin.api.ao.BaseResult;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
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

}
