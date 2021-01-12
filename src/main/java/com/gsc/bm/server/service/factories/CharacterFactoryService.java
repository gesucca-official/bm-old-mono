package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.Character;

public interface CharacterFactoryService {

    Character craftCharacter(String characterClazz);
}
