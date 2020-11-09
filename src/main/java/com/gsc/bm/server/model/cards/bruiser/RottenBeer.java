package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.Status;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RottenBeer extends AbstractCard {

    public RottenBeer() {
        super("Peroni da 66, calda", "+5 tasso alcolemico, +5 violenza, " +
                "prossimi danni x1.5 perché gliela tiri in testa");
    }

    @Override
    public Set<CardTarget> getCanTarget() {
        return Set.of(CardTarget.SELF);
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of();
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        game.getSelf(move).getCharacter().getStatuses().add(
                new Status(
                        "PERONI VUOTA IN MANO",
                        "danni inflitti x1.5",
                        Status.StatusType.GOOD,
                        Status.StatusFlow.OUTPUT,
                        Damage.DamageType.HIT,
                        (damageDone -> damageDone * 1.5f),
                        1
                ));
        return new CardResolutionReport(
                List.of(
                        "peroni status",
                        game.getSelf(move).getCharacter().gainResource(Resource.ALCOHOL, 5),
                        game.getSelf(move).getCharacter().gainResource(Resource.VIOLENCE, 5)
                ),
                null
        );
    }
}
