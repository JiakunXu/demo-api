package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.BaseResult;

/**
 * @author JiakunXu
 */
public interface SecurityCheckService {

    String HTTPS_MSG_URL = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=$ACCESS_TOKEN$";

    /**
     *
     * @param accessToken
     * @param content
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/sec-check/security.msgSecCheck.html">微信官方文档</a>
     */
    BaseResult msgSecCheck(String accessToken, String content) throws RuntimeException;

}
