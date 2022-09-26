package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.wxa.PhoneNumber;

/**
 * @author JiakunXu
 */
public interface PhoneNumberService {

    String HTTPS_POST_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

    /**
     * 
     * @param appid
     * @param accessToken 接口调用凭证
     * @param code 手机号获取凭证
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/phonenumber/phonenumber.getPhoneNumber.html">微信官方文档</a>
     */
    PhoneNumber getPhoneNumber(String appid, String accessToken,
                               String code) throws RuntimeException;

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
