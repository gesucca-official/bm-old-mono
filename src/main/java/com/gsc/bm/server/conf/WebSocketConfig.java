package com.gsc.bm.server.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompSubProtocolErrorHandlerConfig stompSubProtocolErrorHandlerConfig;

    @Autowired
    public WebSocketConfig(StompSubProtocolErrorHandlerConfig stompSubProtocolErrorHandlerConfig) {
        this.stompSubProtocolErrorHandlerConfig = stompSubProtocolErrorHandlerConfig;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/user", "/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/bm-server")
                .setAllowedOrigins("*")
                .withSockJS();
        registry.setErrorHandler(stompSubProtocolErrorHandlerConfig);
    }

}