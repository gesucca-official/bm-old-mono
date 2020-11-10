package com.gsc.bm.server.model.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Card {

    enum CardTarget {
        SELF, OPPONENT
    }

    String getName();

    // description only, no game logic purpose
    @JsonProperty
    String getEffect();

    // the name is hideous but it translates to canTarget
    Set<CardTarget> getCanTarget();

    boolean isFixed();

    Map<Resource, Integer> getCost();

    CardResolutionReport resolve(Game game, Move move);

    // high priority: resolved first (2 before 1)
    default int getPriority() {
        return 1;
    }

    @AllArgsConstructor
    @Getter
    class CardResolutionReport {
        List<String> selfReport;
        List<String> targetReport;
    }

}
