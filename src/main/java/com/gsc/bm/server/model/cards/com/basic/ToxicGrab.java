package com.gsc.bm.server.model.cards.com.basic;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.com.Grab;

import java.util.List;

public class ToxicGrab extends Grab {

    public ToxicGrab() {
        setBasicAction(true);
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return super.mergeList(
                List.of(self.loseResource(Resource.ALERTNESS, self.getResources().get(Resource.ALERTNESS) / 2)),
                super.applyEffectOnSelf(self)
        );
    }
}
