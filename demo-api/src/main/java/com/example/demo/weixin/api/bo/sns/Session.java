package com.example.demo.weixin.api.bo.sns;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class Session extends BaseResult {

    private static final long serialVersionUID = 4107709675927719697L;

    /**
     * 用户唯一标识.
     */
    private String            openid;

    /**
     * 会话密钥.
     */
    @JSONField(name = "session_key")
    private String            sessionKey;

    /**
     * 用户在开放平台的唯一标识符.
     */
    private String            unionid;

}
