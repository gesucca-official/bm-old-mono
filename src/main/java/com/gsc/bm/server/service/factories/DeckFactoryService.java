package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.Card;

import java.util.List;

public interface DeckFactoryService {

    List<Card> craftCharacterStarterDeck(String pgClazz);

    Card craftBasicActionCard(Character character);

    List<Card> craftCharacterBoundCards(Character character);

    Card craftLastResortCard(Character character);
}
