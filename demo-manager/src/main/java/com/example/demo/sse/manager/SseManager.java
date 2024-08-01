package com.example.demo.sse.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JiakunXu
 */
public class SseManager {

    private static final Logger                  logger  = LoggerFactory
        .getLogger(SseManager.class);

    private static final Map<String, SseEmitter> EMITTER = new ConcurrentHashMap<>();

    /**
     *
     * @param key
     * @return
     */
    public static SseEmitter get(String key) {
        return EMITTER.get(key);
    }

    /**
     *
     * @param key
     * @param emitter
     * @return
     */
    public static SseEmitter put(String key, SseEmitter emitter) {
        return EMITTER.put(key, emitter);
    }

    /**
     *
     * @param key
     * @return
     */
    public static SseEmitter remove(String key) {
        SseEmitter sseEmitter = EMITTER.remove(key);

        if (sseEmitter != null) {
            // sseEmitter.complete();
        }

        return sseEmitter;
    }

}
