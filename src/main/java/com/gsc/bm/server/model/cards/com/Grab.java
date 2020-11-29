package com.gsc.bm.server.model.cards.com;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.cards.AbstractItemCard;
import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.IllegalMoveException;
import com.gsc.bm.server.model.game.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Grab extends AbstractCard {

    private Card targetItem;

    public Grab() {
        super();
        setCanTarget(Set.of(CardTarget.NEAR_ITEM));
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
        try {
            targetItem = g.getItem(m.getPlayerId(), m.getChoices().get(Move.AdditionalAction.TARGET_ITEM));
        } catch (IllegalMoveException e) {
            targetItem = null;
            return;
        }
        g.getSelf(m).getCharacter().getItems().remove(targetItem);
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        if (targetItem != null)
            return mergeList(
                    List.of("Grabbed and consumed Item " + targetItem.getName()),
                    ((AbstractItemCard) targetItem).applyEffectOnSelf(self)
            );
        else return List.of("Couldn't Grab that Item!");
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        if (targetItem != null)
            return ((AbstractItemCard) targetItem).applyEffectOnTarget(self, target);
        else return null;
    }

    private <T> List<T> mergeList(List<T> list1, List<T> list2) {
        List<T> mergedList = new ArrayList<>(list1.size() + list2.size());
        mergedList.addAll(list1);
        mergedList.addAll(list2);
        return mergedList;
    }
}
