package com.example.demo.api.dingtalk;

/**
 * @author JiakunXu
 */
public interface TokenService {

    /**
     *
     * @param appKey
     * @param appSecret
     * @return
     * @throws RuntimeException
     */
    String getToken(String appKey, String appSecret) throws RuntimeException;

}
