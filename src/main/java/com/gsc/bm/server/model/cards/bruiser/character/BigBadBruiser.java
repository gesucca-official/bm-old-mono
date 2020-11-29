package com.gsc.bm.server.model.cards.bruiser.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.com.Struggle;

import java.util.Set;

public class BigBadBruiser extends Character {

    public static final String NAME = "Spazienzio de la Ucciso";

    public BigBadBruiser() {
        super(NAME, 100, 20, 1);
        getResources().put(Resource.VIOLENCE, 25);
    }

    @Override
    public Set<Class<?>> getCharacterBoundCards() {
        return Set.of(LittleSmack.class, LittleRage.class);
    }

    @Override
    public Class<?> getLastResortCard() {
        return Struggle.class;
    }

}