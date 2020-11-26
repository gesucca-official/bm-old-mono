package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.bruiser.character.BigBadBruiser;
import com.gsc.bm.server.model.cards.com.Struggle;
import com.gsc.bm.server.model.cards.junkie.character.ToxicJunkie;
import com.gsc.bm.server.model.game.ComPlayer;
import com.gsc.bm.server.model.game.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerFactoryServiceImpl implements PlayerFactoryService {

    private final DeckFactoryService deckFactoryService;
    private final CardFactoryService cardFactoryService;

    @Autowired
    public PlayerFactoryServiceImpl(DeckFactoryService deckFactoryService, CardFactoryService cardFactoryService) {
        this.deckFactoryService = deckFactoryService;
        this.cardFactoryService = cardFactoryService;
    }

    @Override
    public Player craftRandomComPlayer() {
        Character chosenChar = randomPlayer();
        return new ComPlayer(chosenChar,
                deckFactoryService.craftCharacterBoundCards(chosenChar),
                cardFactoryService.craftCard(Struggle::new),
                deckFactoryService.craftCharacterStarterDeck(chosenChar.getClass().getName()));
    }

    @Override
    public Player craftRandomPlayer(String playerId) {
        Character chosenChar = randomPlayer();
        return new Player(playerId,
                chosenChar,
                deckFactoryService.craftCharacterBoundCards(chosenChar),
                cardFactoryService.craftCard(Struggle::new),
                deckFactoryService.craftCharacterStarterDeck(chosenChar.getClass().getName()));
    }

    private Character randomPlayer() {
        int random = (int) ((Math.random() * 100) % 2);
        if (random == 1)
            return new BigBadBruiser();
        else return new ToxicJunkie();
    }
}
