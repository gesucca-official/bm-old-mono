package com.gsc.bm.server.model.cards.bruiser.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.cards.CharacterBoundCard;

import java.util.List;
import java.util.Set;

public class LittleSmack extends CharacterBoundCard {

    public LittleSmack() {
        super("Spazienzio de la Ucciso");
        setCanTarget(Set.of(CardTarget.OPPONENT));
    }

    @Override
    protected List<String> applyEffectOnTarget(Character self, Character target) {
        return List.of(
                self.inflictDamage(target, new Damage(Damage.DamageType.HIT, 5))
        );
    }

}
