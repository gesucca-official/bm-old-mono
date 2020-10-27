package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.Card;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Map;

public class SmackInDaFace implements Card {

    @Override
    public String getName() {
        return "ORMALE SCHIAFFEGGIAMO IL TU::::";
    }

    @Override
    public String getEffect() {
        return "20 danni + 1 ogni 5 di violenza che ti rimane";
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.ofEntries(
                Map.entry(Resource.VIOLENCE, 10)
        );
    }

    @Override
    public void resolve(Game game, Move move) {
        int damage = 20 + (game.getSelf(move).getResources().get(Resource.VIOLENCE) / 5);
        game.getTarget(move).loseResource(Resource.HEALTH, damage);
    }
}
