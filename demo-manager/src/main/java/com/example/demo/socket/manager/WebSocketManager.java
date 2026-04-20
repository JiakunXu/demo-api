package com.example.demo.socket.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JiakunXu
 */
@Slf4j
public class WebSocketManager {

    private static final Map<String, WebSocketSession> SESSION = new ConcurrentHashMap<>();

    /**
     *
     * @param key
     * @return
     */
    public static WebSocketSession get(String key) {
        return SESSION.get(key);
    }

    /**
     *
     * @param key
     * @param session
     * @return
     */
    public static void put(String key, WebSocketSession session) {
        SESSION.put(key, session);
    }

    /**
     *
     * @param key
     * @return
     */
    public static void remove(String key) {
        WebSocketSession session = SESSION.remove(key);

        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("{}", key, e);
            }
        }
    }

}
