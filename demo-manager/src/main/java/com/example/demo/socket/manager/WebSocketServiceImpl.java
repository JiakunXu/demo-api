package com.example.demo.socket.manager;

import com.example.demo.socket.api.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @author JiakunXu
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServiceImpl.class);

    @Override
    public void sendMessage(String tunnelId, String message) {
        WebSocketSession session = WebSocketManager.get(tunnelId);

        if (session == null) {
            return;
        }

        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage("message:" + message));
            } catch (IOException e) {
                logger.error("sendMessage", e);
            }
        }
    }

}
