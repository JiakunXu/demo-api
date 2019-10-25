package com.example.demo.api.weixin;

import com.example.demo.api.weixin.ao.JsapiTicket;

/**
 * @author JiakunXu
 */
public interface JsapiTicketService {

    String HTTPS_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=$ACCESS_TOKEN$&type=jsapi";

    /**
     *
     * @param accessToken
     * @return
     * @throws RuntimeException
     */
    JsapiTicket getJsapiTicket(String accessToken) throws RuntimeException;

}
