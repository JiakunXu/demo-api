package com.example.demo.sse.api;

/**
 * @author JiakunXu
 */
public interface SseService {

    Object init(String tunnelId);

    Object init(String tunnelId, Long timeout);

    void send(String tunnelId, String message);

    void complete(String tunnelId);

    void completeWithError(String tunnelId, Throwable ex);

}
