package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.Status;
import com.gsc.bm.server.model.cards.AbstractCard;

import java.util.List;
import java.util.Set;

public class RottenBeer extends AbstractCard {

    public RottenBeer() {
        super();
        setCanTarget(Set.of(CardTarget.SELF));
    }

    @Override
    protected List<String> applyEffectOnSelf(Character self) {
        self.getStatuses().add(
                new Status(
                        "PERONI VUOTA IN MANO",
                        "danni inflitti x1.5",
                        Status.StatusType.GOOD,
                        Status.StatusFlow.OUTPUT,
                        Damage.DamageType.HIT,
                        (damageDone -> damageDone * 1.5f),
                        1
                ));
        return List.of(
                "peroni status",
                self.gainResource(Resource.ALCOHOL, 5),
                self.gainResource(Resource.VIOLENCE, 5)
        );
    }

}
