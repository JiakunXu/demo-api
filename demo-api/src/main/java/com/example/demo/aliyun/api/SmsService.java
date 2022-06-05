package com.example.demo.aliyun.api;

import com.example.demo.aliyun.api.bo.sms.Data;

/**
 * @author JiakunXu
 */
public interface SmsService {

    /**
     * 
     * @param signName
     * @param templateCode
     * @param templateParam
     * @param phoneNumbers
     * @return
     */
    Data send(String signName, String templateCode, String templateParam, String phoneNumbers);

}
