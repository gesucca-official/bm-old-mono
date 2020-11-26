package com.gsc.bm.server.model.cards.com;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.cards.AbstractItemCard;
import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Set;

public class Grab extends AbstractCard {

    protected Card targetItem;

    public Grab() {
        super();
        setCanTarget(Set.of(CardTarget.NEAR_ITEM));
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
        targetItem = g.getSelf(m).getCharacter().getItem(
                m.getChoices().get(Move.AdditionalAction.TARGET_ITEM));
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return ((AbstractItemCard) targetItem).applyEffectOnSelf(self);
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return ((AbstractItemCard) targetItem).applyEffectOnTarget(self, target);
    }

}
