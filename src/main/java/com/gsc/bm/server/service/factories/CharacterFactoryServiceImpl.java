package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.Character;
import org.springframework.stereotype.Service;

@Service
public class CharacterFactoryServiceImpl implements CharacterFactoryService {

    @Override
    public Character craftCharacter(String characterClazz) {
        try {
            return (Character) Class.forName(CardFactoryService.BASE_CARDS_PKG + characterClazz).getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // this should really be managed better
        }
    }

}
