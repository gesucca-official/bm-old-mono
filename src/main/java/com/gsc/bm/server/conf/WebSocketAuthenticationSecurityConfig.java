package com.gsc.bm.server.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99) // a guy on SO told me it's important
public class WebSocketAuthenticationSecurityConfig implements WebSocketMessageBrokerConfigurer {

    private final AuthChannelInterceptorAdapter authChannelInterceptorAdapter;

    @Autowired
    public WebSocketAuthenticationSecurityConfig(AuthChannelInterceptorAdapter authChannelInterceptorAdapter) {
        this.authChannelInterceptorAdapter = authChannelInterceptorAdapter;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptorAdapter);
    }

}