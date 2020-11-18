package com.gsc.bm.server.stomp;

import com.gsc.bm.server.service.session.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class PresenceEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ConnectionsService connectionsService;

    @Autowired
    public PresenceEventListener(SimpMessagingTemplate messagingTemplate, ConnectionsService connectionsService) {
        this.messagingTemplate = messagingTemplate;
        this.connectionsService = connectionsService;
    }

    @EventListener
    public synchronized void handleSessionConnected(SessionConnectEvent event) {
        connectionsService.userConnected(event);
        broadcastUsers();
    }

    @EventListener
    public synchronized void handleSessionDisconnect(SessionDisconnectEvent event) {
        connectionsService.userDisconnected(event);
        broadcastUsers();
    }

    @MessageMapping("/connections/users/tell/me")
    public void broadcastUsers() {
        messagingTemplate.convertAndSend("/topic/connections/users", connectionsService.getConnectedUsers());
    }
}
