package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Typing;

/**
 * @author JiakunXu
 */
public interface CustomerServiceMessageService {

    String HTTPS_TYPING_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=";

    /**
     * 下发客服当前输入状态给用户.
     * @param accessToken
     * @param toUser
     * @param typing
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/customer-message/customerServiceMessage.setTyping.html">微信官方文档</a>
     */
    BaseResult setTyping(String accessToken, String toUser, Typing typing) throws RuntimeException;

}
