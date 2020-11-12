package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StunningBlow extends AbstractCard {

    public StunningBlow() {
        super();
        setCanTarget(Set.of(CardTarget.OPPONENT));
        setCost(Map.of(Resource.VIOLENCE, 15));
    }

    @Override
    protected List<String> applyEffectOnTarget(Character self, Character target) {
        return List.of(
                target.loseResource(Resource.ALERTNESS, 10),
                self.inflictDamage(target,
                        new Damage(Damage.DamageType.HIT, 10))
        );
    }

}
