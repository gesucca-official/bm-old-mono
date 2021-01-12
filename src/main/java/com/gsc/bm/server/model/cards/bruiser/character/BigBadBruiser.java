package com.gsc.bm.server.model.cards.bruiser.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;

public class BigBadBruiser extends Character {

    public BigBadBruiser() {
        super(100, 20, 1);
        getResources().put(Resource.VIOLENCE, 25);
    }

}