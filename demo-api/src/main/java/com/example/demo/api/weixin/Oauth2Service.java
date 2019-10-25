package com.example.demo.api.weixin;

import com.example.demo.api.weixin.ao.sns.AccessToken;
import com.example.demo.api.weixin.ao.sns.UserInfo;

/**
 * @author JiakunXu
 */
public interface Oauth2Service {

    String HTTPS_AUTHORIZE_URL    = "https://open.weixin.qq.com/connect/oauth2/authorize";

    String HTTPS_QRCONNECT_URL    = "https://open.weixin.qq.com/connect/qrconnect";

    String HTTPS_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";

    String HTTPS_USER_INFO_URL    = "https://api.weixin.qq.com/sns/userinfo?access_token=$ACCESS_TOKEN$&openid=$OPENID$&lang=$LANG$";

    /**
     * 第一步：用户同意授权，获取code.
     * 
     * @param appId 公众号的唯一标识.
     * @param redirectUrl 授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理.
     * @param scope 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）.
     * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节.
     * @return
     * @throws RuntimeException
     */
    String authorize(String appId, String redirectUrl, String scope,
                     String state) throws RuntimeException;

    /**
     * 第二步：通过code换取网页授权access_token.
     * 
     * @param appId 公众号的唯一标识.
     * @param appSecret 公众号的appsecret.
     * @param code 填写第一步获取的code参数.
     * @return
     * @throws RuntimeException
     */
    AccessToken getAccessToken(String appId, String appSecret, String code) throws RuntimeException;

    /**
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo).
     *
     * @param accessToken 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同.
     * @param openId 用户的唯一标识.
     * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语.
     * @return
     * @throws RuntimeException
     */
    UserInfo getUserInfo(String accessToken, String openId, String lang) throws RuntimeException;

}
