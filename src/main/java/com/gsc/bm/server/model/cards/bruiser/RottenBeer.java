package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Attribute;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.Status;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Map;

public class RottenBeer extends AbstractCard {

    public RottenBeer() {
        super("Peroni da 66, calda", "+5 tasso alcolemico, +5 violenza, " +
                "prossimi danni x1.5 perché gliela tiri in testa");
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of(Resource.ALERTNESS, 5);
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        game.getSelf(move).getCharacter().getStatuses().add(
                new Status(
                        "PERONI VUOTA IN MANO",
                        "danni inflitti x1.5",
                        Status.StatusType.GOOD,
                        Attribute.STRENGTH,
                        (strengthSupplier -> strengthSupplier.get() * 1.5f),
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
