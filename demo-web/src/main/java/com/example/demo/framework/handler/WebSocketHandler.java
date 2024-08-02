package com.example.demo.framework.handler;

import com.example.demo.security.api.bo.LoginUser;
import com.example.demo.security.authentication.AuthenticationToken;
import com.example.demo.tunnel.api.TunnelService;
import com.example.demo.socket.manager.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author JiakunXu
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Autowired
    private TunnelService       tunnelService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LoginUser user = (LoginUser) ((AuthenticationToken) session.getPrincipal()).getPrincipal();

        if (user == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        String tunnelId = tunnelService.insertTunnel(user.getId(), null, user.getName())
            .getTunnelId();

        session.getAttributes().put("tunnelId", tunnelId);

        WebSocketManager.put(tunnelId, session);
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

            tunnelService.deleteTunnel(tunnelId, "sys");
        }
    }

}
