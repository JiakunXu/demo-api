package com.example.demo.bytedance.api.bo.token;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 7946434239031585635L;

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
