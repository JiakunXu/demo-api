package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.sns.Session;

/**
 * @author JiakunXu
 */
public interface Code2SessionService {

    String HTTPS_CODE_2_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    /**
     *
     * @param appid 小程序 appId
     * @param secret 小程序 appSecret
     * @param jsCode 登录时获取的 code
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">微信官方文档</a>
     */
    Session getSession(String appid, String secret, String jsCode) throws RuntimeException;

}
