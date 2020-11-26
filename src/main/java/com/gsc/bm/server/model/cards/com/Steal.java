package com.gsc.bm.server.model.cards.com;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.Set;

public class Steal extends Grab {

    public Steal() {
        super();
        setCanTarget(Set.of(CardTarget.FAR_ITEM));
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
        targetItem = g.getTarget(m).getCharacter().getItem(
                m.getChoices().get(Move.AdditionalAction.TARGET_ITEM));
    }
}
