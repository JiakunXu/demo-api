package com.example.demo.dingtalk.api;

public interface TokenService {

    String getToken(String appKey, String appSecret) throws RuntimeException;

}
