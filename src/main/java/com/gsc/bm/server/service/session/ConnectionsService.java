package com.gsc.bm.server.service.session;

public interface ConnectionsService {

    void userConnected();

    void userDisconnected();

    int getConnectedUsers();
}
