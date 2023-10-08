package com.example.demo.dingtalk.api;

public interface AccessTokenService {

    String getAccessToken(String appKey, String appSecret) throws RuntimeException;

}
