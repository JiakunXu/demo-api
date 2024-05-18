package com.example.demo.weixin.api.bo.token;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class AccessToken extends BaseResult {

    @Serial
    private static final long serialVersionUID = 2754621311801850489L;

    /**
     * 获取到的凭证.
     */
    @JSONField(name = "access_token")
    private String            accessToken;

    /**
     * 凭证有效时间，单位：秒.
     */
    @JSONField(name = "expires_in")
    private int               expiresIn;

}
