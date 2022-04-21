package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.message.Image;
import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Template;
import com.example.demo.weixin.api.bo.message.Text;
import com.example.demo.weixin.api.bo.message.Voice;

/**
 * @author JiakunXu
 */
public interface MessageService {

    String HTTPS_CUSTOM_URL   = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    String HTTPS_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 发送文本消息.
     *
     * @param accessToken
     * @param toUser
     * @param text
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Service_Center_messages.html#7">微信官方文档</a>
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/customer-message/customerServiceMessage.send.html">微信官方文档</a>
     */
    Result send(String accessToken, String toUser, Text text) throws RuntimeException;

    /**
     * 发送图片消息.
     * 
     * @param accessToken
     * @param toUser
     * @param image
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Service_Center_messages.html#7">微信官方文档</a>
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/customer-message/customerServiceMessage.send.html">微信官方文档</a>
     */
    Result send(String accessToken, String toUser, Image image) throws RuntimeException;

    /**
     * 发送语音消息.
     *
     * @param accessToken
     * @param toUser
     * @param voice
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Service_Center_messages.html#7">微信官方文档</a>
     */
    Result send(String accessToken, String toUser, Voice voice) throws RuntimeException;

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
