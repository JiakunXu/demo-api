package com.example.demo.alipay.api.bo.token;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class OauthToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 5477020768486377159L;

    /**
     * 用户支付宝 ID。
     */
    @JSONField(name = "user_id")
    private String            userId;

    /**
     * 访问令牌。通过该令牌调用需要授权类接口。
     */
    @JSONField(name = "access_token")
    private String            accessToken;

    /**
     * 访问令牌的有效时间，单位是秒。
     */
    @JSONField(name = "expires_in")
    private Long              expiresIn;

    /**
     * 刷新令牌。通过该令牌可以刷新 access_token。
     */
    @JSONField(name = "refresh_token")
    private String            refreshToken;

    /**
     * 刷新令牌的有效时间，单位是秒。
     */
    @JSONField(name = "re_expires_in")
    private Long              reExpiresIn;

}
