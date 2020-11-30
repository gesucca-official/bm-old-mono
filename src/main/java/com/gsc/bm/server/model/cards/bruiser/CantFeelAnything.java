package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.status.Status;
import com.gsc.bm.server.model.game.status.StatusFlow;
import com.gsc.bm.server.model.game.status.StatusType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CantFeelAnything extends AbstractCard {

    public CantFeelAnything() {
        super();
        setCanTarget(Set.of(CardTarget.SELF));
        setCost(Map.of(Resource.VIOLENCE, 20));
        setPriority(2);
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        self.getStatuses().add(
                Status.builder()
                        .name("YOU CARESS ME")
                        .description("Hit Damage Taken: x0.35")
                        .type(StatusType.GOOD)
                        .flow(StatusFlow.INPUT)
                        .impactedProperty(Damage.DamageType.HIT)
                        .amountFunction(incomingDamage -> incomingDamage * 0.35f)
                        .lastsForTurns(0)
                        .build()
        );
        return List.of(
                "Gained status: YOU CARESS ME",
                self.gainResource(Resource.ALERTNESS, 10)
        );
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return null;
    }

}
