package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Map;

public class SickeningBlow extends AbstractCard {

    public SickeningBlow() {
        super("ORMALE AMMALIAMOTI DI BOTTE", "10 danni e 10 tossicità al target");
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of(Resource.VIOLENCE, 20);
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        return new CardResolutionReport(
                null,
                List.of(
                        game.getSelf(move).getCharacter().inflictDamage(
                                game.getTarget(move).getCharacter(),
                                new Damage(Damage.DamageType.HIT, 10)),
                        game.getTarget(move).getCharacter().gainResource(Resource.TOXICITY, 10)
                ));
    }
}
