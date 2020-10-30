package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Map;

public class CocktailOnTheGround extends AbstractCard {

    public CocktailOnTheGround() {
        super("Negroni trovato per terra", "+5 toxic +10 tasso alcolemico e +20 violenza");
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of(Resource.ALERTNESS, 5);
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        return new CardResolutionReport(
                Map.ofEntries(
                        game.getSelf(move).getCharacter().gainResource(Resource.VIOLENCE, 20),
                        game.getSelf(move).getCharacter().gainResource(Resource.ALCOHOL, 10),
                        game.getSelf(move).getCharacter().gainResource(Resource.TOXICITY, 5)
                ),
                null
        );
    }
}