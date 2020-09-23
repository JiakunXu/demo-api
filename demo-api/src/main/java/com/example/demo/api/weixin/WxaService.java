package com.example.demo.api.weixin;

import com.example.demo.api.weixin.ao.WxaCode;

/**
 * @author JiakunXu
 */
public interface WxaService {

    String HTTPS_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=$ACCESS_TOKEN$";

    /**
     *
     * @param accessToken
     * @param wxaCode
     * @return
     * @throws RuntimeException
     */
    WxaCode getWxaCodeUnlimit(String accessToken, WxaCode wxaCode) throws RuntimeException;

}
