package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SickeningBlow extends AbstractCard {

    public SickeningBlow() {
        super();
        setCanTarget(Set.of(CardTarget.OPPONENT));
        setCost(Map.of(Resource.VIOLENCE, 15));
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return null;
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return List.of(
                self.inflictDamage(
                        target, new Damage(Damage.DamageType.HIT, 10)),
                target.gainResource(Resource.TOXICITY, 10)
        );
    }

}
