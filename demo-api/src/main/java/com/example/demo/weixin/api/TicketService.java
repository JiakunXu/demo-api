package com.example.demo.weixin.api;

/**
 * @author JiakunXu
 */
public interface TicketService {

    /**
     *
     * @param appId
     * @param appSecret
     * @return
     * @throws RuntimeException
     */
    String getTicket(String appId, String appSecret) throws RuntimeException;

}
