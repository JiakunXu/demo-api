package com.example.demo.sse.api;

/**
 * @author JiakunXu
 */
public interface SseService {

    void send(String tunnelId, String message);

}
