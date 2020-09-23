package com.example.demo.api.dingtalk;

import com.example.demo.api.dingtalk.ao.User;

/**
 * @author JiakunXu
 */
public interface UserService {

    String HTTPS_USER_URL = "https://oapi.dingtalk.com/user/get";

    /**
     * 
     * @param accessToken
     * @param userId
     * @param lang
     * @return
     * @throws RuntimeException
     */
    User getUser(String accessToken, String userId, String lang) throws RuntimeException;

}
