package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.Status;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Glare extends AbstractCard {

    public Glare() {
        super("PAZIENZA FINITA::::", "danni subiti x1.5 in questo turno, ma violenza +30 alertness +20");
    }

    @Override
    public Set<CardTarget> getCanTarget() {
        return Set.of(CardTarget.SELF);
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Collections.emptyMap();
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        game.getSelf(move).getCharacter().getStatuses().add(
                new Status(
                        "PAZIENZA FINITA",
                        "danni subiti x1.5",
                        Status.StatusType.BAD,
                        Status.StatusFlow.INPUT,
                        Damage.DamageType.HIT,
                        (incomingDamage -> incomingDamage * 1.5f),
                        1
                ));

        return new CardResolutionReport(
                List.of(
                        "status PAZIENZA FINITA",
                        game.getSelf(move).getCharacter().gainResource(Resource.VIOLENCE, 30),
                        game.getSelf(move).getCharacter().gainResource(Resource.ALERTNESS, 20)
                ),
                null
        );
    }
}
