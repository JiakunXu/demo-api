package com.example.demo.framework.handler;

import com.example.demo.api.cache.RedisService;
import com.example.demo.api.tunnel.TunnelService;
import com.example.demo.api.tunnel.bo.Tunnel;
import com.example.demo.api.user.bo.User;
import com.example.demo.socket.manager.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

/**
 * @author JiakunXu
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger        logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Autowired
    private RedisService<String, User> redisService;

    @Autowired
    private TunnelService              tunnelService;

    @Value("${server.address}")
    private String                     address;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();

        Object token = attributes.get("token");
        Object appId = attributes.get("appId");

        if (token == null || appId == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        User user = getUser((String) token);

        if (user == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        Tunnel tunnel = tunnelService.insertTunnel(user.getId(), address);

        session.getAttributes().put("tunnelId", tunnel.getTunnelId());

        WebSocketManager.put(tunnel.getTunnelId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {
        if ("ping".equals(message.getPayload())) {
            session.sendMessage(new TextMessage("pong"));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        if (!CloseStatus.BAD_DATA.equals(status)) {
            String tunnelId = (String) session.getAttributes().get("tunnelId");

            WebSocketManager.remove(tunnelId);
        }
    }

    /**
     * 
     * @param skey
     * @return
     */
    private User getUser(String skey) {
        try {
            return redisService.get(skey);
        } catch (Exception e) {
            logger.error("getUser", e);
        }

        return null;
    }

}
