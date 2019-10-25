package com.example.demo.api.weixin;

/**
 * @author JiakunXu
 */
public interface TokenService {

    /**
     * 
     * @param grantType
     * @param appId
     * @param appSecret
     * @return
     * @throws RuntimeException
     */
    String getToken(String grantType, String appId, String appSecret) throws RuntimeException;

}
