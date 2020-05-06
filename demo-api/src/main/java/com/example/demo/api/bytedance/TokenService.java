package com.example.demo.api.bytedance;

/**
 * @author JiakunXu
 */
public interface TokenService {

    /**
     *
     * @param appId
     * @param appSecret
     * @param grantType
     * @return
     * @throws RuntimeException
     */
    String getToken(String appId, String appSecret, String grantType) throws RuntimeException;

}
