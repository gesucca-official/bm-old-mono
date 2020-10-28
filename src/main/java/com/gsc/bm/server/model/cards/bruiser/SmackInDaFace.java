package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Map;

public class SmackInDaFace extends AbstractCard {

    public SmackInDaFace() {
        super("SPEDIAMOTI DUNQUE IN O-AHIO", "20 danni + 1 per ogni 2 di Violenza che ti rimane");
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.ofEntries(
                Map.entry(Resource.VIOLENCE, 15)
        );
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        int damage = game.getTarget(move).getCharacter().loseResource(
                Resource.HEALTH,
                20 + (game.getSelf(move).getResources().get(Resource.VIOLENCE) / 2));
        return new CardResolutionReport("-15 violenza", "hai inflitto " + damage + " danni");
    }
}