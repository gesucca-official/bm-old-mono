package com.gsc.bm.server.stomp;

import com.gsc.bm.server.service.session.ConnectionsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
public class PresenceEventListener {

    private final ConnectionsService connectionsService;

    @Autowired
    public PresenceEventListener(ConnectionsService connectionsService) {
        this.connectionsService = connectionsService;
    }

    @EventListener
    @SendTo("/topic/connections/user/join")
    public String handleSessionConnected(SessionConnectEvent event) {
        Object rawNativeHeaders = event.getMessage().getHeaders().get("nativeHeaders");
        if (rawNativeHeaders == null) {
            log.info("SessionConnectEvent does not have the expected Native Headers!");
            return null;
        }
        Map<String, Object> nativeHeaders = new HashMap<>((Map<? extends String, ?>) rawNativeHeaders);
        log.info(nativeHeaders.get("login"));
        log.info(event);
        return "";
    }

    @EventListener
    @SendTo("/topic/connections/user/leave")
    public String handleSessionDisconnect(SessionDisconnectEvent event) {
        log.info("disconnection event");
        log.info(event);
        return "";
    }

}
