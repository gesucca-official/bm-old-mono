package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Set;

public class CocktailOnTheGround extends AbstractCard {

    public CocktailOnTheGround() {
        super();
        setCanTarget(Set.of(CardTarget.SELF));
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return List.of(
                self.gainResource(Resource.VIOLENCE, 20),
                self.gainResource(Resource.ALCOHOL, 10),
                self.gainResource(Resource.TOXICITY, 5)
        );
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return null;
    }

}