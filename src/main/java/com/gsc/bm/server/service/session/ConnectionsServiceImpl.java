package com.gsc.bm.server.service.session;

import org.springframework.stereotype.Service;

@Service
public class ConnectionsServiceImpl implements ConnectionsService {

    private static int _CONNECTIONS = 0;

    @Override
    public synchronized void userConnected() {
        _CONNECTIONS++;
    }

    @Override
    public synchronized void userDisconnected() {
        _CONNECTIONS--;
    }

    @Override
    public synchronized int getConnectedUsers() {
        return _CONNECTIONS;
    }
}
