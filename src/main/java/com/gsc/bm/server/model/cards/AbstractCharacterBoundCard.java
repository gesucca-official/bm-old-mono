package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Map;

public abstract class AbstractCharacterBoundCard extends AbstractCard {

    public AbstractCharacterBoundCard(String boundTo) {
        super();
        setCharacterBound(true);
        setBoundToCharacter(boundTo);
        setCost(Map.of());
    }

    @Override
    public Map<CardTarget, List<String>> resolve(Game game, Move move) {
        Card toBeDiscarded = game.getCardFromHand(
                move.getPlayerId(),
                move.getChoices().get(Move.AdditionalAction.DISCARD_ONE));
        game.getSelf(move).discardCard(toBeDiscarded);
        return super.resolve(game, move);
    }

}
