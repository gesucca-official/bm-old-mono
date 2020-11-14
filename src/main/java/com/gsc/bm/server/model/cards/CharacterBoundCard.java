package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

public abstract class CharacterBoundCard extends AbstractCard {

    public CharacterBoundCard(String boundTo) {
        super();
        setCharacterBound(true);
        setBoundToCharacter(boundTo);
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        Card toBeDiscarded = game.getCardFromHand(
                move.getPlayerId(),
                move.getChoices().get(Move.AdditionalAction.DISCARD_ONE));
        game.getSelf(move).discardCard(toBeDiscarded);
        return super.resolve(game, move);
    }

}
