package com.gsc.bm.server.service.session;

import com.gsc.bm.server.service.session.model.UserSessionInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashSet;
import java.util.Set;

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
        messagingTemplate.convertAndSend("/topic/connections/users", _USERS);
    }

    @Override
    public void userConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userLoginName = accessor.getLogin();
        String sessionId = accessor.getSessionId();
        log.info("User Connected! " + userLoginName + " with simpSessionId " + sessionId);
        _USERS.add(new UserSessionInfo(userLoginName, sessionId, UserSessionInfo.Activity.FREE));
    }

    @Override
    public String userDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String userLoginName = accessor.getLogin();
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
        return userLoginName;
    }

    @Override
    public void userActivityChanged(String userId, UserSessionInfo.Activity activity) {
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
