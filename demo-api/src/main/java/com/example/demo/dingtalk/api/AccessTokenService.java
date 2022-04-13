package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.AccessToken;

/**
 * @author JiakunXu
 */
public interface AccessTokenService {

    String HTTPS_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";

    /**
     * 获取access_token.
     * 
     * @param appKey 应用的唯一标识key
     * @param appSecret 应用的密钥
     * @return
     * @throws RuntimeException
     */
    AccessToken getAccessToken(String appKey, String appSecret) throws RuntimeException;

}
