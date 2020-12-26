package com.gsc.bm.server.conf;

import com.gsc.bm.server.service.session.AuthService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {

    private final AuthService connectionsService;

    public AuthChannelInterceptorAdapter(AuthService connectionsService) {
        this.connectionsService = connectionsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;
        if (accessor.getCommand() == StompCommand.CONNECT)
            connectionsService.getAuthToken(accessor.getLogin(), accessor.getPasscode())
                    .ifPresentOrElse(accessor::setUser, () -> {
                        throw new AccessDeniedException("Authentication Failed");
                    });
        return message;
    }
}
