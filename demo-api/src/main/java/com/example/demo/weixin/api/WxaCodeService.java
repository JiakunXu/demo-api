package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.wxa.WxaCode;

/**
 * @author JiakunXu
 */
public interface WxaCodeService {

    String HTTPS_GET_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";

    /**
     *
     * @param accessToken
     * @param wxaCode
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html">微信官方文档</a>
     */
    byte[] getWxaCodeUnlimit(String accessToken, WxaCode wxaCode) throws RuntimeException;

}
