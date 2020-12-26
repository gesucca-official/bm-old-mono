package com.gsc.bm.server.service.session;

import com.gsc.bm.server.service.session.model.UserSessionInfo;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public interface ConnectionsService {

    void broadcastUsersInfo();

    void userConnected(SessionConnectEvent event);

    String userDisconnected(SessionDisconnectEvent event);

    void userActivityChanged(String userId, UserSessionInfo.Activity activity);

}
