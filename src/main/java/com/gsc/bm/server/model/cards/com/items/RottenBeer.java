package com.gsc.bm.server.model.cards.com.items;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractItemCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.status.Status;
import com.gsc.bm.server.model.game.status.StatusFlow;
import com.gsc.bm.server.model.game.status.StatusType;

import java.util.List;

public class RottenBeer extends AbstractItemCard {

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
        g.getSelf(m).getCharacter().getStatuses().add(
                Status.builder()
                        .name("ROTTEN BEER")
                        .description("Hit Damage dealt x1.5 and turned to Cut Damage")
                        .type(StatusType.GOOD)
                        .flow(StatusFlow.OUTPUT)
                        .impactedProperty(Damage.DamageType.HIT)
                        .amountFunction(damageDone -> damageDone * 1.5f)
                        .typeFunction(damageType -> damageType == Damage.DamageType.HIT ? Damage.DamageType.CUT : damageType)
                        .lastsForTurns(1)
                        .build()
        );
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return List.of(
                "Gained status: ROTTEN BEER",
                self.gainResource(Resource.ALCOHOL, 5)
        );
    }

}
