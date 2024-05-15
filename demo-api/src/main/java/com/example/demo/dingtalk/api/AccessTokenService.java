package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.AccessToken;

public interface AccessTokenService {

    AccessToken getAccessToken(String appKey, String appSecret) throws RuntimeException;

}
