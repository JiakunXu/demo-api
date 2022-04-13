package com.example.demo.dingtalk.api;

/**
 * @author JiakunXu
 */
public interface TicketService {

    /**
     *
     * @param appKey
     * @param appSecret
     * @return
     * @throws RuntimeException
     */
    String getTicket(String appKey, String appSecret) throws RuntimeException;

}
