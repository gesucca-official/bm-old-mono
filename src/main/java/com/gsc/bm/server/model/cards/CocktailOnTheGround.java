package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.Card;
import com.gsc.bm.server.model.Game;
import com.gsc.bm.server.model.Resource;

import java.util.Map;

public class CocktailOnTheGround implements Card {

    @Override
    public String getName() {
        return "Negroni trovato per terra";
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return null;
    }

    @Override
    public void resolve(Game game, String targetPlayerId) {

    }
}
