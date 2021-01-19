package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.game.Player;

public interface PlayerFactoryService {

    Player craftRandomComPlayer();

    Player craftRandomPlayer(String playerId);

    Player craftOpenPlayer(String username, String deckId);
}
