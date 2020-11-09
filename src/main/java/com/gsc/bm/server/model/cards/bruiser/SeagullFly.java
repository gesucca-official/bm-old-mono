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

public class SeagullFly extends AbstractCard {

    public SeagullFly() {
        super("GABBIANO? VOLIAMOTI", "20 danni, ignora status di protezione");
    }

    @Override
    public Set<CardTarget> getCanTarget() {
        return Set.of(CardTarget.OPPONENT);
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of(Resource.VIOLENCE, 10);
    }

    @Override
    public CardResolutionReport resolve(Game game, Move move) {
        return new CardResolutionReport(
                null,
                List.of(
                        game.getSelf(move).getCharacter().inflictDamage(
                                game.getTarget(move).getCharacter(),
                                new Damage(Damage.DamageType.HIT, 20),
                                Set.of(Status.StatusType.GOOD))
                ));
    }
}
