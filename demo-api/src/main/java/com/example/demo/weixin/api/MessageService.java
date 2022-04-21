package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Template;

/**
 * @author JiakunXu
 */
public interface MessageService {

    String HTTPS_CUSTOM_URL   = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    String HTTPS_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 模板消息.
     * 
     * @param accessToken
     * @param toUser
     * @param template
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html#5">微信官方文档</a>
     */
    Result send(String accessToken, String toUser, Template template) throws RuntimeException;

}
