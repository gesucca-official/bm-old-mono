package com.gsc.bm.server.service;

import com.gsc.bm.server.model.Card;

import java.util.List;

public interface DeckFactoryService {

    List<Card> craftCharacterStarterDeck(String characterName);
}
