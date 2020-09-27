package com.example.demo.api.dingtalk;

import com.example.demo.api.dingtalk.ao.JsapiTicket;

/**
 * @author JiakunXu
 */
public interface JsapiTicketService {

    String HTTPS_TICKET_URL = "https://oapi.dingtalk.com/get_jsapi_ticket";

    /**
     *
     * @param accessToken
     * @return
     * @throws RuntimeException
     */
    JsapiTicket getJsapiTicket(String accessToken) throws RuntimeException;

}
