package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Map;

public class SeagullFly extends AbstractCard {

    public SeagullFly() {
        super("GABBIANO? VOLIAMOTI", "20 danni, bypassa protezioni");
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of(Resource.VIOLENCE, 15);
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        int damage = game.getTarget(move).getCharacter().loseResource(Resource.ALERTNESS, 20, false, true);
        return new CardResolutionReport(
                "pagato 15 violenza",
                damage + " danni"
        );
    }
}
