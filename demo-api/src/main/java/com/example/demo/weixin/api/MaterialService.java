package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.material.Count;

/**
 * @author JiakunXu
 */
public interface MaterialService {

    String HTTPS_COUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=";

    /**
     * 
     * @param accessToken
     * @return
     * @throws RuntimeException
     */
    Count count(String accessToken) throws RuntimeException;

}
