package com.gsc.bm.server.model.cards;

import java.util.Map;
import java.util.Set;

public abstract class AbstractObjectCard extends AbstractCard {

    public AbstractObjectCard() {
        super();
        setObject(true);
        setCanTarget(Set.of(CardTarget.SELF));
        setCost(Map.of());
    }
}
