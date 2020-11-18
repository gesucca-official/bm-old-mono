package com.gsc.bm.server.stomp;

import com.gsc.bm.server.service.session.ConnectionsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
@Log4j2
public class PresenceEventListener {

    private final ConnectionsService connectionsService;

    @Autowired
    public PresenceEventListener(ConnectionsService connectionsService) {
        this.connectionsService = connectionsService;
    }

    @EventListener
    @SendTo("/topic/connections/")
    public String handleSessionConnected(SessionConnectEvent event) {
        connectionsService.userConnected();
        log.info("Client Connected! " + event.getMessage().getHeaders().get("nativeHeaders") + "  Total users connected: " + connectionsService.getConnectedUsers());
        log.info(event);
        return "";
    }

    @EventListener
    @SendTo("/topic/connections/")
    public String handleSessionDisconnect(SessionDisconnectEvent event) {
        log.info("disconnection event");
        log.info(event);
        return "";
    }

}
