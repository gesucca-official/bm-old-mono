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
        List<Card> deck = new ArrayList<>();
        deck.add(new CocktailOnTheGround());
        deck.add(new CocktailOnTheGround());
        deck.add(new CocktailOnTheGround());
        deck.add(new SmackInDaFace());
        deck.add(new SmackInDaFace());
        deck.add(new Glare());
        deck.add(new Glare());
        deck.add(new StunningBlow());
        deck.add(new StunningBlow());
        deck.add(new StunningBlow());
        deck.add(new SeagullFly());
        deck.add(new SeagullFly());
        return deck;
    }
}
