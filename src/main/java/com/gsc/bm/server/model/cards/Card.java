package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Card {

    @AllArgsConstructor
    @Getter
    class CardResolutionReport {
        List<String> selfReport;
        List<String> targetReport;
    }

    enum CardTarget {
        SELF, OPPONENT
    }

    String getName();

    String getEffect();

    boolean isCharacterBound();

    Optional<String> boundToCharacter();

    int getPriority();  // higher priority resolved first (2 before 1)

    Set<CardTarget> getCanTarget();

    Map<Resource, Integer> getCost();

    CardResolutionReport resolve(Game game, Move move);

}
