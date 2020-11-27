package com.gsc.bm.server.model.cards.junkie;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.*;

public class BeggingMoney extends AbstractCard {

    String message;

    public BeggingMoney() {
        super();
        setCanTarget(Set.of(CardTarget.OPPONENT));
        setCost(Map.of(Resource.TOXICITY, 25));
        setPriority(0);
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
        List<Card> items = new ArrayList<>(g.getTarget(m).getCharacter().getItems());
        Collections.shuffle(items);
        if (!items.isEmpty()) {
            g.getSelf(m).getCharacter().getItems().add(items.get(0));
            message = "You begged and received " + items.get(0);
        } else message = "You begged but received nothing!";
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return null;
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return List.of(
                target.loseResource(Resource.ALERTNESS, 10)
        );
    }
}
