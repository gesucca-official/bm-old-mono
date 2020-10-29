package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Map;

public class StunningBlow extends AbstractCard {

    public StunningBlow() {
        super("STORDIAMOTI HO DETTO", "10 danni e -10 attenzione al target");
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of(Resource.VIOLENCE, 10);
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        int alertnessDamage = game.getTarget(move).getCharacter().loseResource(Resource.ALERTNESS, 10);
        int damage = game.getTarget(move).getCharacter().loseResource(Resource.HEALTH, 10);
        return new CardResolutionReport(
                "pagato 10 violenza",
                damage + " danno e -" + alertnessDamage + " alertness all'oppo"
        );
    }
}
