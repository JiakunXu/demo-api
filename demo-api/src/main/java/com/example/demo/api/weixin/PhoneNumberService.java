package com.example.demo.api.weixin;

import com.example.demo.api.weixin.ao.sns.PhoneNumber;

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
