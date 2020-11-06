package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.Status;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Map;

public class CantFeelAnything extends AbstractCard {

    public CantFeelAnything() {
        super("GENTILO IL TUO CAREZZO::", "first strike, +10 alertness e dimezza i danni da botte in questo turno");
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of(Resource.VIOLENCE, 20);
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        game.getSelf(move).getCharacter().getStatuses().add(
                new Status(
                        "MI FAI LE CAREZZE",
                        "danni subiti x0.5",
                        Status.StatusType.GOOD,
                        Status.StatusFlow.INPUT,
                        Damage.DamageType.HIT,
                        (incomingDamage -> incomingDamage * 0.5f),
                        0
                ));

        return new CardResolutionReport(
                List.of(
                        "carezze status",
                        game.getSelf(move).getCharacter().gainResource(Resource.ALERTNESS, 10)
                ), null
        );
    }
}
