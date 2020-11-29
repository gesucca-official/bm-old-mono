package com.gsc.bm.server.model.cards.junkie.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.com.basic.ShuffleFeet;
import com.gsc.bm.server.model.cards.com.last.Struggle;

import java.util.Set;

public class ToxicJunkie extends Character {

    public static final String NAME = "Tossico del Serraglio";

    public ToxicJunkie() {
        super(NAME, 100, 35, 3);
        getResources().put(Resource.TOXICITY, 10);
        getImmunities().add(Resource.TOXICITY);
    }

    @Override
    public Class<?> getBasicActionCard() {
        return ShuffleFeet.class;
    }

    @Override
    public Set<Class<?>> getCharacterBoundCards() {
        return Set.of(PatheticBlade.class, RottenSmile.class);
    }

    @Override
    public Class<?> getLastResortCard() {
        return Struggle.class;
    }

}
