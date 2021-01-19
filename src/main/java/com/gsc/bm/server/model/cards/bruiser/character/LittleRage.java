package com.gsc.bm.server.model.cards.bruiser.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCharacterBoundCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.status.Status;
import com.gsc.bm.server.model.game.status.StatusFlow;
import com.gsc.bm.server.model.game.status.StatusType;

import java.util.List;
import java.util.Set;

public class LittleRage extends AbstractCharacterBoundCard {

    public LittleRage() {
        super(BigBadBruiser.class);
        setCanTarget(Set.of(CardTarget.SELF));
        setPriority(2);
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        self.getStatuses().add(
                Status.builder()
                        .name("ONLY A LITTLE DISCOMFORT")
                        .description("Hit Damage taken: x0.75")
                        .type(StatusType.GOOD)
                        .flow(StatusFlow.INPUT)
                        .impactedProperty(Damage.DamageType.HIT)
                        .amountFunction(incomingDamage -> incomingDamage * 0.75f)
                        .lastsForTurns(0)
                        .build()
        );

        return List.of(
                "Gained status: ONLY DISCOMFORT",
                self.gainResource(Resource.VIOLENCE, 5)
        );
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return null;
    }
}
