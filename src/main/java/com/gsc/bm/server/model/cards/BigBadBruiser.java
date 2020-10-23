package com.gsc.bm.server.model.cards;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;

public class BigBadBruiser extends Character {

    public BigBadBruiser() {
        super("Spazienzio de la Ucciso" ,100, 20);
        resources.put(Resource.VIOLENCE, 30);
    }

}
