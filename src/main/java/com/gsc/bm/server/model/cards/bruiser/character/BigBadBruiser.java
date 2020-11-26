package com.gsc.bm.server.model.cards.bruiser.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;

import java.util.Set;

public class BigBadBruiser extends Character {

    public static final String NAME = "Spazienzio de la Ucciso";

    public BigBadBruiser() {
        super(NAME, 100, 20, attentionSpan);
        resources.put(Resource.VIOLENCE, 25);
    }

    @Override
    public Set<String> getCharacterBoundCards() {
        return Set.of(LittleSmack.class.getName(), LittleRage.class.getName());
    }

}