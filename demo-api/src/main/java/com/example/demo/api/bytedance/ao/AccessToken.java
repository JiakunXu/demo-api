package com.example.demo.api.bytedance.ao;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author JiakunXu
 */
public class AccessToken extends BaseResult {

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

}
