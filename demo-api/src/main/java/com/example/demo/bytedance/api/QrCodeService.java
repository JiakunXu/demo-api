package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.bo.qrcode.Body;

/**
 * @author JiakunXu
 */
public interface QrCodeService {

    String HTTPS_CREATE_URL = "https://developer.toutiao.com/api/apps/qrcode";

    /**
     * 
     * @param accessToken
     * @param body
     * @throws RuntimeException
     */
    void create(String accessToken, Body body) throws RuntimeException;

}
