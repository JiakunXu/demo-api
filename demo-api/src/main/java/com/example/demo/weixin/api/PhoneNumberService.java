package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.sns.PhoneNumber;

/**
 * @author JiakunXu
 */
public interface PhoneNumberService {

    /**
     *
     * @param appId
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     * @throws RuntimeException
     */
    PhoneNumber getPhoneNumber(String appId, String encryptedData, String sessionKey,
                               String iv) throws RuntimeException;

}
