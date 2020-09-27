package com.example.demo.dingtalk.service.impl;

import com.example.demo.api.dingtalk.JsapiService;
import com.example.demo.api.dingtalk.ao.Jsapi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("jsapiService0")
public class JsapiServiceImpl implements JsapiService {

    private static final Logger logger = LoggerFactory.getLogger(JsapiServiceImpl.class);

    @Override
    public Jsapi getJsapi(String agentId, String url) {
        return null;
    }

}
