package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.Card;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Map;

public class CocktailOnTheGround implements Card {

    @Override
    public String getName() {
        return "Negroni trovato per terra";
    }

    @Override
    public String getEffect() {
        return "+20 violenza";
    }

    @Override
    public Map<Resource, Integer> getCost() {
        // TODO this is just an example, this card should not cost anything
        return Map.ofEntries(
                Map.entry(Resource.SPEED, 5),
                Map.entry(Resource.HEALTH, 5)
        );
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        game.getSelf(move).gainResource(Resource.VIOLENCE, 20);
        return new CardResolutionReport(
                "hai bevuto un negroni trovato per terra, come credi di stare",
                "niente"
        );
    }
}
