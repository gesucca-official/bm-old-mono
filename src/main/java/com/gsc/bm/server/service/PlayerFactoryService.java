package com.gsc.bm.server.service;

import com.gsc.bm.server.model.game.Player;

public interface PlayerFactoryService {

    Player craftRandomPlayer(String playerId);
}
