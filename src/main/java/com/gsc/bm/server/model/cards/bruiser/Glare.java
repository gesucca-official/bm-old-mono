package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.Status;
import com.gsc.bm.server.model.cards.AbstractCard;

import java.util.List;
import java.util.Set;

public class Glare extends AbstractCard {

    public Glare() {
        super();
        setCanTarget(Set.of(CardTarget.SELF));
    }

    @Override
    protected List<String> applyEffectOnSelf(Character self) {
        self.getStatuses().add(
                new Status(
                        "NO PATIENCE",
                        "damage taken x1.5",
                        Status.StatusType.BAD,
                        Status.StatusFlow.INPUT,
                        Damage.DamageType.HIT,
                        (incomingDamage -> incomingDamage * 1.5f),
                        1
                ));
        return List.of(
                "Gained status: NO PATIENCE",
                self.gainResource(Resource.VIOLENCE, 15),
                self.gainResource(Resource.ALERTNESS, 15)
        );
    }

}
