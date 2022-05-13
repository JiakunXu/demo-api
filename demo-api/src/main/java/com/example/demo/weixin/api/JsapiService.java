package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.js.Jsapi;

/**
 * @author JiakunXu
 */
public interface JsapiService {

    /**
     * 
     * @param url
     * @return
     */
    Jsapi getJsapi(String url);

}
