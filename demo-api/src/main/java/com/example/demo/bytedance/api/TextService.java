package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.bo.text.Body;
import com.example.demo.bytedance.api.bo.text.Log;

/**
 * @author JiakunXu
 */
public interface TextService {

    String HTTPS_POST_URL = "https://developer.toutiao.com/api/v2/tags/text/antidirt";

    /**
     *
     * @param accessToken
     * @param body
     * @return
     * @throws RuntimeException
     */
    Log detect(String accessToken, Body body) throws RuntimeException;

}
