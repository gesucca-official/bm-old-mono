package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.Resource;

import java.util.Map;

public abstract class FixedCard extends AbstractCard {

    public FixedCard(String name, String effect) {
        super(name, effect);
    }

    @Override
    public boolean isFixed() {
        return true;
    }

    @Override
    public Map<Resource, Integer> getCost() {
        return Map.of();
    }
}
