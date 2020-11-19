package com.gsc.bm.server.service.session;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@Service
@Log4j2
public class ConnectionsServiceImpl implements ConnectionsService {

    private static final Set<UserSessionInfo> _USERS = new HashSet<>();

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ConnectionsServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void broadcastUsersInfo() {
        messagingTemplate.convertAndSend("/topic/connections/users", getConnectedUsers());
    }

    @Override
    public void userConnected(SessionConnectEvent event) {
        Object rawNativeHeaders = event.getMessage().getHeaders().get("nativeHeaders");
        if (rawNativeHeaders == null)
            log.info("SessionConnectEvent does not have the expected Native Headers!");

        String userLoginName = ((List<String>) (new HashMap<>((Map<? extends String, ?>) rawNativeHeaders).get("login"))).get(0);
        String sessionId = event.getMessage().getHeaders().get("simpSessionId", String.class);

        log.info("User Connected! " + userLoginName + " with simpSessionId " + sessionId);
        _USERS.add(new UserSessionInfo(userLoginName, sessionId, UserSessionInfo.Activity.FREE));
    }

    @Override
    public void userDisconnected(SessionDisconnectEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId", String.class);
        log.info(_USERS);
        // TODO extract this into private method
        _USERS.stream()
                .filter(userSessionInfo -> userSessionInfo.getSessionId().equals(sessionId))
                .findAny()
                .ifPresentOrElse(
                        userSessionInfo -> {
                            log.info("User Disconnected: " + userSessionInfo.getUserLogin() + " with simpSessionId: " + sessionId);
                            _USERS.remove(userSessionInfo);
                        },
                        () -> log.info("An User not known as attempted Disconnection!")
                );
    }

    @Override
    public Set<UserSessionInfo> getConnectedUsers() {
        return _USERS;
    }

    @Override
    public void changeUserActivity(String userId, UserSessionInfo.Activity activity) {
        _USERS.stream()
                .filter(userSessionInfo -> userSessionInfo.getUserLogin().equals(userId))
                .findAny()
                .ifPresentOrElse(
                        userSessionInfo -> userSessionInfo.setActivity(activity),
                        () -> log.info("An User not known as attempted to change Activity!")
                );
        broadcastUsersInfo();
    }

}
