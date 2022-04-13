package com.example.demo.weixin.api.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class AccessToken extends BaseResult {

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
