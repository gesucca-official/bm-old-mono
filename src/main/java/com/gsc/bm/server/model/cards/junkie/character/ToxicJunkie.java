package com.gsc.bm.server.model.cards.junkie.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;

import java.util.Set;

public class ToxicJunkie extends Character {

    public static final String NAME = "Tossico del Serraglio";

    public ToxicJunkie() {
        super(NAME, 100, 35);
        resources.put(Resource.TOXICITY, 10);
        getImmunities().add(Resource.TOXICITY);
    }

    @Override
    public Set<String> getCharacterBoundCards() {
        return Set.of(PatheticBlade.class.getName(), RottenSmile.class.getName());
    }

}
