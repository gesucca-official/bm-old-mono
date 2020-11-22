package com.gsc.bm.server.service.session;

import com.gsc.bm.server.service.session.model.UserSessionInfo;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;

public interface ConnectionsService {

    void broadcastUsersInfo();

    void userConnected(SessionConnectEvent event);

    void userDisconnected(SessionDisconnectEvent event);

    Set<UserSessionInfo> getConnectedUsers();

    void changeUserActivity(String userId, UserSessionInfo.Activity activity);
}
