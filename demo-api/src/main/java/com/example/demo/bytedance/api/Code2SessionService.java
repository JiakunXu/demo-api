package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.ao.Session;

/**
 * @author JiakunXu
 */
public interface Code2SessionService {

    String HTTPS_CODE_2_SESSION_URL = "https://developer.toutiao.com/api/apps/jscode2session";

    /**
     *
     * @param appId 小程序 ID.
     * @param appSecret 小程序的 APP Secret，可以在开发者后台获取.
     * @param code login接口返回的登录凭证.
     * @return
     * @throws RuntimeException
     */
    Session getSession(String appId, String appSecret, String code) throws RuntimeException;

}
