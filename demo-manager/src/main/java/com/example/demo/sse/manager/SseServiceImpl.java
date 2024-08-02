package com.example.demo.sse.manager;

import com.example.demo.sse.api.SseService;
import com.example.demo.tunnel.api.TunnelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @author JiakunXu
 */
@Service
public class SseServiceImpl implements SseService {

    private static final Logger logger = LoggerFactory.getLogger(SseServiceImpl.class);

    @Autowired
    private TunnelService       tunnelService;

    @Override
    public Object init(String tunnelId) {
        return init(tunnelId, null);
    }

    @Override
    public Object init(String tunnelId, Long timeout) {
        SseEmitter emitter = timeout == null ? new SseEmitter() : new SseEmitter(timeout);

        emitter.onTimeout(() -> {
            SseManager.remove(tunnelId);
            tunnelService.deleteTunnel(tunnelId, "sys");
        });

        emitter.onError((e) -> {
            SseManager.remove(tunnelId);
            tunnelService.deleteTunnel(tunnelId, "sys");
        });

        emitter.onCompletion(() -> {
            SseManager.remove(tunnelId);
            tunnelService.deleteTunnel(tunnelId, "sys");
        });

        SseManager.put(tunnelId, emitter);

        return emitter;
    }

    @Override
    public void send(String tunnelId, String message) {
        SseEmitter emitter = SseManager.get(tunnelId);

        if (emitter == null) {
            return;
        }

        try {
            emitter.send(message);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }

    @Override
    public void complete(String tunnelId) {
        SseEmitter emitter = SseManager.get(tunnelId);

        if (emitter == null) {
            return;
        }

        emitter.complete();
    }

    @Override
    public void completeWithError(String tunnelId, Throwable ex) {
        SseEmitter emitter = SseManager.get(tunnelId);

        if (emitter == null) {
            return;
        }

        emitter.completeWithError(ex);
    }

}
