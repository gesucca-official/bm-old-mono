package com.gsc.bm.server.model;

import java.util.Map;

public interface Card {

    String getName();

    Map<Resource, Integer> getCost();

    void resolve(Game game, String targetPlayerId);

    default boolean isCastable(Map<Resource, Integer> playerResources) {
        for (Resource r : getCost().keySet())
            if (playerResources.get(r) < getCost().get(r))
                return false;
        return true;
    }
}
