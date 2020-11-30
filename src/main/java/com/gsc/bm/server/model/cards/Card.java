package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Card {

    UnknownCard UNKNOWN_CARD = new UnknownCard();

    enum CardTarget {
        SELF, OPPONENT, NEAR_ITEM, FAR_ITEM
    }

    String getName();

    String getEffect();

    boolean isItem();

    boolean isBasicAction();

    boolean isLastResort();

    boolean isCharacterBound();

    Optional<String> boundToCharacter();

    int getPriority();  // higher priority resolved first (2 before 1)

    Set<CardTarget> getCanTarget();

    Map<Resource, Integer> getCost();

    Map<CardTarget, List<String>> resolve(Game game, Move move);

}
