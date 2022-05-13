package com.example.demo.dingtalk.api.bo.token;

import com.example.demo.dingtalk.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class AccessToken extends BaseResult {

    private static final long serialVersionUID = 7946434239031585635L;

    /**
     * 获取到的凭证.
     */
    private String            accessToken;

    /**
     * 凭证有效时间，单位：秒.
     */
    private Long              expiresIn;

}
