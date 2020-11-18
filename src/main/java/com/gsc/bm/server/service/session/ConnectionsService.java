package com.gsc.bm.server.service.session;

import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public interface ConnectionsService {

    void userConnected(SessionConnectedEvent event);

    void userDisconnected(SessionDisconnectEvent event);

    int getConnectedUsers();
}
