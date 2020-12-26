package com.gsc.bm.server.conf;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

@Component
public class StompSubProtocolErrorHandlerConfig extends StompSubProtocolErrorHandler {

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        if (ex instanceof AccessDeniedException) {
            return MessageBuilder.createMessage(ex.getMessage().getBytes(), StompHeaderAccessor.create(StompCommand.ERROR).getMessageHeaders());
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }
}
