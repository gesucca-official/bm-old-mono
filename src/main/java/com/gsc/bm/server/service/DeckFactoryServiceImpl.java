package com.gsc.bm.server.service;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.cards.bruiser.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeckFactoryServiceImpl implements DeckFactoryService {

    @Override
    public List<Card> craftCharacterStarterDeck(String characterName) {
        // TODO do it
        if (characterName.equalsIgnoreCase("Spazienzio de la Ucciso")) {
            List<Card> deck = new ArrayList<>();
            deck.add(new CocktailOnTheGround());
            deck.add(new CocktailOnTheGround());
            deck.add(new Glare());
            deck.add(new Glare());
            deck.add(new StunningBlow());
            deck.add(new StunningBlow());
            deck.add(new SeagullFly());
            deck.add(new SeagullFly());
            deck.add(new CantFeelAnything());
            deck.add(new CantFeelAnything());
            deck.add(new RottenBeer());
            deck.add(new RottenBeer());


            deck.add(new HealingBlow());
            deck.add(new SickeningBlow());
            deck.add(new ExtremelyViolentBlow());
            return deck;
        } else return null;
    }
}
