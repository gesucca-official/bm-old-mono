package com.gsc.bm.server.model.cards.com;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.IllegalMoveException;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Set;

public class Steal extends AbstractCard {

    private String target;
    private Card targetItem;

    public Steal() {
        super();
        setCanTarget(Set.of(CardTarget.FAR_ITEM));
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
        target = m.getTargetId();
        try {
            targetItem = g.getItem(m.getTargetId(), m.getChoices().get(Move.AdditionalAction.TARGET_ITEM));
        } catch (IllegalMoveException e) {
            targetItem = null;
            return;
        }
        g.getTarget(m).getCharacter().getItems().remove(targetItem);
        g.getSelf(m).getCharacter().getItems().add(targetItem);
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return List.of("Stolen " + targetItem.getName() + " from " + target);
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return null;
    }
}
