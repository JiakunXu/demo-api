package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.bo.token.AccessToken;

/**
 * @author JiakunXu
 */
public interface AccessTokenService {

    String HTTPS_POST_URL = "https://developer.toutiao.com/api/apps/v2/token";

    /**
     *
     * @param appId 小程序 ID.
     * @param appSecret 小程序的 APP Secret，可以在开发者后台获取.
     * @param grantType 获取 access_token 时值为 client_credential.
     * @return
     * @throws RuntimeException
     */
    AccessToken getAccessToken(String appId, String appSecret,
                               String grantType) throws RuntimeException;

}
