package com.gsc.bm.server.service.session;

import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;

public interface ConnectionsService {

    void userConnected(SessionConnectEvent event);

    void userDisconnected(SessionDisconnectEvent event);

    Set<String> getConnectedUsers();
}
