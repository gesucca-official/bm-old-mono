package com.gsc.bm.server.service;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.game.Player;
import com.gsc.bm.server.model.cards.BigBadBruiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerFactoryServiceImpl implements PlayerFactoryService {

    private final DeckFactoryService deckFactoryService;

    @Autowired
    public PlayerFactoryServiceImpl(DeckFactoryService deckFactoryService) {
        this.deckFactoryService = deckFactoryService;
    }

    @Override
    public Player craftRandomPlayer(String playerId) {
        Character chosenChar = new BigBadBruiser();
        return new Player(playerId, chosenChar, deckFactoryService.craftCharacterStarterDeck(chosenChar.getName()));
    }
}
