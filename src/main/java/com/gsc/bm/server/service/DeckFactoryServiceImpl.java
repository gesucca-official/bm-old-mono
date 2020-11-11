package com.gsc.bm.server.service;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.cards.bruiser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeckFactoryServiceImpl implements DeckFactoryService {

    private final CardFactoryService cardFactoryService;

    @Autowired
    public DeckFactoryServiceImpl(CardFactoryService cardFactoryService) {
        this.cardFactoryService = cardFactoryService;
    }

    @Override
    public List<Card> craftCharacterStarterDeck(String characterName) {
        // TODO do it
        if (characterName.equalsIgnoreCase("Spazienzio de la Ucciso")) {
            List<Card> deck = new ArrayList<>();
            deck.add(cardFactoryService.craftCard(CocktailOnTheGround::new));
            deck.add(cardFactoryService.craftCard(Glare::new));
            deck.add(cardFactoryService.craftCard(StunningBlow::new));
            deck.add(cardFactoryService.craftCard(CantFeelAnything::new));
            deck.add(cardFactoryService.craftCard(RottenBeer::new));
            deck.add(cardFactoryService.craftCard(SeagullFly::new));
            deck.add(cardFactoryService.craftCard(HealingBlow::new));
            deck.add(cardFactoryService.craftCard(SickeningBlow::new));
            deck.add(cardFactoryService.craftCard(ExtremelyViolentBlow::new));
            return deck;
        } else return null;
    }
}
