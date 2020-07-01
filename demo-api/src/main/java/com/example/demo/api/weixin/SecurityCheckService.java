package com.example.demo.api.weixin;

import com.example.demo.api.weixin.ao.BaseResult;

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
     */
    BaseResult msgSecCheck(String accessToken, String content) throws RuntimeException;

}
