package com.example.demo.api.aliyun;

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
    String send(String signName, String templateCode, String templateParam, String phoneNumbers);

}
