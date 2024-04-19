package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.bo.session.Session;

/**
 * @author JiakunXu
 */
public interface Code2SessionService {

    String HTTPS_GET_URL = "https://developer.toutiao.com/api/apps/jscode2session?appid={0}&secret={1}&code={2}";

    /**
     *
     * @param appid 小程序 ID.
     * @param secret 小程序的 APP Secret，可以在开发者后台获取.
     * @param code login接口返回的登录凭证.
     * @return
     * @throws RuntimeException
     */
    Session getSession(String appid, String secret, String code) throws RuntimeException;

}
