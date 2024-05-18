package com.example.demo.dingtalk.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AccessToken implements Serializable {

    @Serial
    private static final long serialVersionUID = -3894640525011355121L;

    /**
     * 获取到的凭证.
     */
    private String            accessToken;

    /**
     * 凭证有效时间，单位：秒.
     */
    private Long              expireIn;

}
