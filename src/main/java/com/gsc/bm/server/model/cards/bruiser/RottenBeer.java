package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.status.Status;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.status.StatusFlow;
import com.gsc.bm.server.model.game.status.StatusType;

import java.util.List;
import java.util.Set;

public class RottenBeer extends AbstractCard {

    public RottenBeer() {
        super();
        setCanTarget(Set.of(CardTarget.SELF));
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        self.getStatuses().add(
                Status.builder()
                        .name("ROTTEN BEER")
                        .description("Hit Damage dealt x1.5 and turned to Cut Damage")
                        .type( StatusType.GOOD)
                        .flow(StatusFlow.OUTPUT)
                        .impactedProperty(Damage.DamageType.HIT)
                        .amountFunction(damageDone -> damageDone * 1.5f)
                        .typeFunction(damageType -> damageType == Damage.DamageType.HIT ? Damage.DamageType.CUT : damageType)
                        .lastsForTurns(1)
                        .build()
        );
        return List.of(
                "Gained status: ROTTEN BEER",
                self.gainResource(Resource.ALCOHOL, 5)
        );
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return null;
    }

}
