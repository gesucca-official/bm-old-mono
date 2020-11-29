package com.gsc.bm.server.model.cards.com.items;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;

import java.util.List;

public class AnotherRottenBeer extends RottenBeer {

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return List.of(
                "Gained status: ROTTEN BEER",
                self.gainResource(Resource.ALCOHOL, 10)
        );
    }
}
