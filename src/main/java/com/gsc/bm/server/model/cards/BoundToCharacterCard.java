package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Optional;

public abstract class BoundToCharacterCard extends AbstractCard {

    private final String boundTo;

    public BoundToCharacterCard(String boundTo, String name, String effect) {
        super(name, effect);
        this.boundTo = boundTo;
    }

    protected abstract CardResolutionReport resolveCardLogic(Game g, Move m);

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        // TODO downcast move and discard selected card
        return null;
    }

    @Override
    public boolean isBoundToCharacter() {
        return true;
    }

    @Override
    public Optional<String> boundToCharacter() {
        return Optional.of(boundTo);
    }


}
