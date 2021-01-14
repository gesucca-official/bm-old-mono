package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.service.view.model.deck.CharacterCardView;

public interface CharacterFactoryService {

    Character craftCharacter(String characterClazz);

    CharacterCardView craftCharacterView(String characterClazz);
}
