package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.Status;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Set;

public class Glare extends AbstractCard {

    public Glare() {
        super();
        setCanTarget(Set.of(CardTarget.SELF));
        setPriority(0);
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        self.getStatuses().add(
                new Status(
                        "NO PATIENCE - Damage Intake Boost",
                        "Damage taken: x1.5",
                        Status.StatusType.BAD,
                        Status.StatusFlow.INPUT,
                        Damage.DamageType.HIT,
                        (incomingDamage -> incomingDamage * 1.5f),
                        1
                ));
        // TODO you should not lose any alertness or violence while this status lasts...
        return List.of(
                "Gained statuses: NO PATIENCE",
                self.gainResource(Resource.VIOLENCE, 15),
                self.gainResource(Resource.ALERTNESS, 15)
        );
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return null;
    }

}
