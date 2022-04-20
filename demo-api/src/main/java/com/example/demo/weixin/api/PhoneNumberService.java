package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.sns.PhoneNumber;

/**
 * @author JiakunXu
 */
public interface PhoneNumberService {

    /**
     *
     * @param appid
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     * @throws RuntimeException
     */
    PhoneNumber getPhoneNumber(String appid, String encryptedData, String sessionKey,
                               String iv) throws RuntimeException;

}
