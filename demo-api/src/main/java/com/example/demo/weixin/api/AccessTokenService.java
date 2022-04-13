package com.example.demo.weixin.api;

import com.example.demo.weixin.api.ao.AccessToken;

/**
 * @author JiakunXu
 */
public interface AccessTokenService {

    String HTTPS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=$grantType$&appid=$appId$&secret=$appSecret$";

    /**
     *
     * @param grantType 获取access_token填写client_credential.
     * @param appId 第三方用户唯一凭证.
     * @param appSecret 第三方用户唯一凭证密钥，即appsecret.
     * @return
     * @throws RuntimeException
     */
    AccessToken getAccessToken(String grantType, String appId,
                               String appSecret) throws RuntimeException;

}
