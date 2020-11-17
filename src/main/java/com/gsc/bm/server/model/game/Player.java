package com.gsc.bm.server.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.cards.Struggle;
import lombok.Getter;

import java.io.Serializable;
import java.util.*;

@Getter
public class Player implements Serializable {

    // TODO builder pattern for this?

    // TODO expose public information

    private final String playerId;
    private final Character character;
    private final List<Card> cardsInHand = new ArrayList<>();
    private final Stack<Card> deck = new Stack<>();

    // TODO: for now don't let the FE know about this, then we can have each character have a slightly different version of this
    @JsonIgnore private final Card lastResortCard;

    public Player(String id, Character character, List<Card> characterBoundCards, Card lastResortCard, List<Card> deck) {
        this.playerId = id;
        this.character = character;
        this.cardsInHand.addAll(characterBoundCards);
        this.lastResortCard = lastResortCard;

        this.deck.addAll(deck);
        Collections.shuffle(this.deck);
        drawCards(3);
    }

    public void drawCard() {
        if (!deck.isEmpty())
            cardsInHand.add(deck.pop());
        else cardsInHand.add(lastResortCard);
    }

    public void drawCards(int howMany) {
        for (int i = 0; i < howMany; i++)
            drawCard();
    }

    public void discardCard(Card card) {
        if (!card.isCharacterBound())
            cardsInHand.remove(card);
    }

    @JsonIgnore
    public Map<Resource, Integer> getResources() {
        return character.getResources();
    }

}
