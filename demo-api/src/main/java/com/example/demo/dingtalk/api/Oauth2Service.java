package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.AccessToken;

public interface Oauth2Service {

    AccessToken getAccessToken(String appKey, String appSecret) throws RuntimeException;

}
