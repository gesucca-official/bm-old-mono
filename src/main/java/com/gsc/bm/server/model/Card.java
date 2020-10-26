package com.gsc.bm.server.model;

import com.gsc.bm.server.model.game.Game;

import java.util.Map;

public interface Card {

    String getName();

    // description only, no game logic purpose
    String getEffect();

    Map<Resource, Integer> getCost();

    void resolve(Game game, String targetPlayerId);
}
