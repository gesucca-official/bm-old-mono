package com.gsc.bm.server.model.cards;

import java.util.Map;
import java.util.Set;

public abstract class AbstractItemCard extends AbstractCard {

    public AbstractItemCard() {
        super();
        setItem(true);
        setCanTarget(Set.of(CardTarget.SELF));
        setCost(Map.of());
    }
}
