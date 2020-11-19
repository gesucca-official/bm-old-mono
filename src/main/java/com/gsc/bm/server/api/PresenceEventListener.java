package com.gsc.bm.server.api;

import com.gsc.bm.server.service.session.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class PresenceEventListener {

    private final ConnectionsService connectionsService;

    @Autowired
    public PresenceEventListener(ConnectionsService connectionsService) {
        this.connectionsService = connectionsService;
    }

    @EventListener
    public synchronized void handleSessionConnected(SessionConnectEvent event) {
        connectionsService.userConnected(event);
        connectionsService.broadcastUsersInfo();
    }

    @EventListener
    public synchronized void handleSessionDisconnect(SessionDisconnectEvent event) {
        connectionsService.userDisconnected(event);
        connectionsService.broadcastUsersInfo();
    }

    @MessageMapping("/connections/users/tell/me")
    public void broadcastUsers() {
        connectionsService.broadcastUsersInfo();
    }
}