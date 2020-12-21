package com.gsc.bm.server.model.cards.junkie;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Set;

public class PervertedMetabolism extends AbstractCard {

    public PervertedMetabolism() {
        super();
        setCanTarget(Set.of(CardTarget.SELF));
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return List.of(
                self.gainResource(Resource.HEALTH, self.getResources().get(Resource.TOXICITY) / 2),
                self.loseResource(Resource.TOXICITY, self.getResources().get(Resource.TOXICITY))
        );
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return null;
    }
}
