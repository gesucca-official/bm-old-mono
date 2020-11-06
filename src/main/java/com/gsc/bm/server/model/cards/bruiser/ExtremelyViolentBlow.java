package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Map;

public class ExtremelyViolentBlow extends AbstractCard {

    public ExtremelyViolentBlow() {
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
        return new CardResolutionReport(
                null,
                List.of(
                        game.getSelf(move).getCharacter().inflictDamage(game.getTarget(move).getCharacter(),
                                new Damage(Damage.DamageType.HIT, 20 + (game.getSelf(move).getResources().get(Resource.VIOLENCE) / 2))
                        )));
    }
}