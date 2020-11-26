package com.gsc.bm.server.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.Card;
import lombok.Getter;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.io.Serializable;
import java.util.*;

@Getter
public class Player implements Serializable {

    private final String playerId;
    private final Character character;

    private final Queue<Card> objects;
    private final List<Card> cardsInHand = new ArrayList<>();
    private final Stack<Card> deck = new Stack<>();

    private final Card lastResortCard;

    public Player(String id, Character character, List<Card> characterBoundCards, Card lastResortCard, List<Card> deck) {
        this.playerId = id;
        this.character = character;

        this.objects = new CircularFifoQueue<>(character.getObjSize());

        this.cardsInHand.addAll(characterBoundCards);
        this.lastResortCard = lastResortCard;

        this.deck.addAll(deck);
        Collections.shuffle(this.deck);
        drawCards(3);
    }

    @JsonProperty
    public int getDeckSize() {
        return deck.size();
    }

    public void drawCard() {
        if (!deck.isEmpty()) {
            Card card = deck.pop();
            if (card.isObject()) {
                objects.add(card);
                drawCard();
            } else
                cardsInHand.add(card);
        } else if (!cardsInHand.contains(lastResortCard))
            cardsInHand.add(lastResortCard);
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
