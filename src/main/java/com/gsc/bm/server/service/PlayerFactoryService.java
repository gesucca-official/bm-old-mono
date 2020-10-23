package com.gsc.bm.server.service;

import com.gsc.bm.server.model.Player;

public interface PlayerFactoryService {

    Player craftRandomPlayer(String playerId);
}
