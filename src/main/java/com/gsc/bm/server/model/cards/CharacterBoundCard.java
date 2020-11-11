package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.IllegalMoveException;
import com.gsc.bm.server.model.game.Move;

import java.util.Map;
import java.util.Optional;

public abstract class CharacterBoundCard extends AbstractCard {

    private final String boundTo;

    public CharacterBoundCard(String boundTo, String name, String effect) {
        super(name, effect);
        this.boundTo = boundTo;
    }

    protected abstract CardResolutionReport resolveCardLogic(Game g, Move m);

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        Card toBeDiscarded = game.getCardFromHand(
                move.getPlayerId(),
                move.getChoices().get(Move.AdditionalAction.DISCARD_ONE));
        game.getSelf(move).discardCard(toBeDiscarded);
        return resolveCardLogic(game, move);
    }

    @Override
    public boolean isCharacterBound() {
        return true;
    }

    @Override
    public Optional<String> boundToCharacter() {
        return Optional.of(boundTo);
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of();
    }
}
