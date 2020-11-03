package com.gsc.bm.server.model.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public interface Card {

    String getName();

    // description only, no game logic purpose
    @JsonProperty
    String getEffect();

    Map<Resource, Integer> getCost();

    CardResolutionReport resolve(Game game, Move move);

    @AllArgsConstructor
    @Getter
    class CardResolutionReport {
        List<String> selfReport;
        List<String> targetReport;
    }

}
