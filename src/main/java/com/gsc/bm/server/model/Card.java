package com.gsc.bm.server.model;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

public interface Card {

    String getName();

    // description only, no game logic purpose
    String getEffect();

    Map<Resource, Integer> getCost();

    CardResolutionReport resolve(Game game, Move move);

    @AllArgsConstructor
    @Getter
    class CardResolutionReport {
        String selfReport;
        String targetReport;
    }

}
