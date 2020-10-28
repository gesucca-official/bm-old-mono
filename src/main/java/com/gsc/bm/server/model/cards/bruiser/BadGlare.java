package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.StatusAliment;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Collections;
import java.util.Map;

public class BadGlare extends AbstractCard {

    public BadGlare() {
        super("PAZIENZA FINITA::::", "danni subiti x1.5 in questo turno, ma violenza +30 alertness +20");
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Collections.emptyMap();
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        game.getSelf(move).getCharacter().getStatuses().add(
                new StatusAliment(
                        (damage) -> damage + (damage / 2),
                        Resource.HEALTH,
                        1
                )
        );
        game.getSelf(move).getCharacter().gainResource(Resource.VIOLENCE, 30);
        game.getSelf(move).getCharacter().gainResource(Resource.ALERTNESS, 20);
        return new CardResolutionReport(
                "violenza +30 alertness +20, mi fanno più male",
                "niente"
        );
    }
}
