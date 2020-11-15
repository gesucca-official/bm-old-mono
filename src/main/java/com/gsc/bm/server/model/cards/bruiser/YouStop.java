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

public class YouStop extends AbstractCard {

    public YouStop() {
        super();
        setCanTarget(Set.of(CardTarget.OPPONENT));
        setCost(Map.of(Resource.VIOLENCE, 5));
        setPriority(2);
    }

    @Override
    protected void applyOtherUnfathomableLogic(Game g, Move m) {
        if (g.getPendingMovePlayedBy(m.getTargetId()).isPresent())
            g.getPendingMovePlayedBy(m.getTargetId()).get().setVoid(true);
    }

    @Override
    protected List<String> applyEffectOnSelf(Character self) {
        return List.of(
                self.inflictDamage(self, new Damage(Damage.DamageType.HIT, 5))
        );
    }

    @Override
    protected List<String> applyEffectOnTarget(Character self, Character target) {
        return List.of("Countered Target's own Move");
    }
}
