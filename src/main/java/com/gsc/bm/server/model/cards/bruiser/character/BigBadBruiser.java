package com.gsc.bm.server.model.cards.bruiser.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.Card;

import java.util.Set;

public class BigBadBruiser extends Character {

    public BigBadBruiser() {
        super("Spazienzio de la Ucciso", 100, 20);
        resources.put(Resource.VIOLENCE, 50);
    }

    @Override
    public Set<Card> getCharacterBoundCards() {
        return Set.of(new LittleSmack());
    }
}