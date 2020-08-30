package com.example.demo.api.socket;

/**
 * @author JiakunXu
 */
public interface WebSocketService {

    /**
     *
     * @param tunnelId
     * @param message
     */
    void sendMessage(String tunnelId, String message);

}
