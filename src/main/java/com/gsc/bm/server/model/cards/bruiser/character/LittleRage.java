package com.gsc.bm.server.model.cards.bruiser.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.CharacterBoundCard;

import java.util.List;
import java.util.Set;

public class LittleRage extends CharacterBoundCard {

    public LittleRage() {
        super(BigBadBruiser.NAME);
        setCanTarget(Set.of(CardTarget.OPPONENT));
    }

    @Override
    protected List<String> applyEffectOnSelf(Character self) {
        return List.of(
                self.gainResource(Resource.VIOLENCE, 5)
        );
    }
}
