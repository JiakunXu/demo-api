package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.Jsapi;

/**
 * @author JiakunXu
 */
public interface JsapiService {

    /**
     *
     * @param url
     * @return
     */
    Jsapi getJsapi(String agentId, String url);

}
