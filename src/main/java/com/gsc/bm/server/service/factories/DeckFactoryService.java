package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.Card;

import java.util.List;

public interface DeckFactoryService {

    List<Card> craftCharacterStarterDeck(String pgClazz);

    Card craftBasicActionStarterCard(Character character);

    List<Card> craftCharacterBoundStarterCards(Character character);

    Card craftLastResortStarterCard(Character character);

    List<Card> craftCharacterOpenDeck(String username, String deckId);

    Card craftBasicActionOpenCard(String username, String deckId);

    List<Card> craftCharacterBoundOpenCards(String username, String deckId);

    Card craftLastResortOpenCard(String username, String deckId);
}
