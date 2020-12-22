package com.gsc.bm.server.model.cards.junkie;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.com.Grab;

import java.util.List;

public class EverythingIsDrug extends Grab {

    // this extends Grab only to avoid copying the underlying logic of object selection

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return List.of(
                self.gainResource(Resource.TOXICITY, 20)
        );
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return null;
    }

}
