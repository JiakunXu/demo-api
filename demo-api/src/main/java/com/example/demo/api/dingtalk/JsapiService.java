package com.example.demo.api.dingtalk;

import com.example.demo.api.dingtalk.ao.Jsapi;

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
