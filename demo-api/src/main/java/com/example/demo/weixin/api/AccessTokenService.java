package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.token.AccessToken;

/**
 * @author JiakunXu
 */
public interface AccessTokenService {

    String HTTPS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type={0}&appid={1}&secret={2}";

    /**
     *
     * @param grantType 获取access_token填写client_credential.
     * @param appId 第三方用户唯一凭证.
     * @param appSecret 第三方用户唯一凭证密钥，即appsecret.
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">微信官方文档</a>
     */
    AccessToken getAccessToken(String grantType, String appId,
                               String appSecret) throws RuntimeException;

}
