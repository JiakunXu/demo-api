package com.example.demo.api.dingtalk;

import com.example.demo.api.dingtalk.ao.UserInfo;

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
