package com.example.demo.framework.config;

import com.example.demo.framework.handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @author JiakunXu
 */
@Configuration
@EnableWebSocket
public class SocketConfigurer implements WebSocketConfigurer {

    @Autowired
    private HandshakeInterceptor handshakeInterceptor;

    @Autowired
    private WebSocketHandler     webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws").addInterceptors(handshakeInterceptor)
            .setAllowedOrigins("*");
    }

}
