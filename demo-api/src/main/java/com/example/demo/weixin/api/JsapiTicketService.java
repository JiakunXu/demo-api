package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.js.JsapiTicket;

/**
 * @author JiakunXu
 */
public interface JsapiTicketService {

    String HTTPS_GET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

    /**
     *
     * @param accessToken
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/JS-SDK.html">微信官方文档</a>
     */
    JsapiTicket getJsapiTicket(String accessToken) throws RuntimeException;

}
