package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Subscribe;
import com.example.demo.weixin.api.bo.message.Template;
import com.example.demo.weixin.api.bo.message.Uniform;

/**
 * @author JiakunXu
 */
public interface MessageService {

    String HTTPS_TEMPLATE_URL  = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    String HTTPS_SUBSCRIBE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";

    String HTTPS_UNIFORM_URL   = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=";

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

    /**
     * 订阅消息.
     * 
     * @param accessToken
     * @param toUser
     * @param subscribe
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html">微信官方文档</a>
     */
    BaseResult send(String accessToken, String toUser, Subscribe subscribe) throws RuntimeException;

    /**
     * 下发小程序和公众号统一的服务消息.
     *
     * @param accessToken
     * @param toUser
     * @param uniform
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/uniform-message/uniformMessage.send.html">微信官方文档</a>
     *
     */
    BaseResult send(String accessToken, String toUser, Uniform uniform) throws RuntimeException;

}
