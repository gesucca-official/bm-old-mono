package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.bruiser.character.BigBadBruiser;
import com.gsc.bm.server.model.game.ComPlayer;
import com.gsc.bm.server.model.game.Player;
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
    public Player craftRandomComPlayer() {
        Character chosenChar = new BigBadBruiser();
        return new ComPlayer(chosenChar,
                deckFactoryService.craftCharacterBoundCards(chosenChar),
                deckFactoryService.craftCharacterStarterDeck(chosenChar.getClass().getName()));
    }

    @Override
    public Player craftRandomPlayer(String playerId) {
        Character chosenChar = new BigBadBruiser();
        return new Player(playerId,
                chosenChar,
                deckFactoryService.craftCharacterBoundCards(chosenChar),
                deckFactoryService.craftCharacterStarterDeck(chosenChar.getClass().getName()));
    }
}
