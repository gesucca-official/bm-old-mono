package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.Character;

import java.util.List;

public interface DeckFactoryService {

    List<Card> craftCharacterStarterDeck(String pgClazz);

    List<Card> craftCharacterBoundCards(Character character);
}
