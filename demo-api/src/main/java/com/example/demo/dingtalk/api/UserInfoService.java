package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.user.UserInfo;

/**
 * @author JiakunXu
 */
public interface UserInfoService {

    String HTTPS_USER_INFO_URL = "https://oapi.dingtalk.com/user/getuserinfo";

    /**
     * 
     * @param accessToken
     * @param code
     * @throws RuntimeException
     */
    UserInfo getUserInfo(String accessToken, String code) throws RuntimeException;

}
