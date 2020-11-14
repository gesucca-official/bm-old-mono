package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;

import java.util.List;
import java.util.Set;

public class CocktailOnTheGround extends AbstractCard {

    public CocktailOnTheGround() {
        super();
        setCanTarget(Set.of(CardTarget.SELF));
    }

    @Override
    protected List<String> applyEffectOnSelf(Character self) {
        return List.of(
                self.gainResource(Resource.VIOLENCE, 20),
                self.gainResource(Resource.ALCOHOL, 10),
                self.gainResource(Resource.TOXICITY, 5)
        );
    }

}