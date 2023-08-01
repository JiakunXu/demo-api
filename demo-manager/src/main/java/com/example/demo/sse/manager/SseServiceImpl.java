package com.example.demo.sse.manager;

import com.example.demo.sse.api.SseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @author JiakunXu
 */
@Service
public class SseServiceImpl implements SseService {

    private static final Logger logger = LoggerFactory.getLogger(SseServiceImpl.class);

    @Override
    public void send(String tunnelId, String message) {

        SseEmitter emitter = SseManager.get(tunnelId);

        if (emitter == null) {
            return;
        }

        try {
            emitter.send(message);
        } catch (IOException e) {
            logger.error("send", e);
        }
    }

}
