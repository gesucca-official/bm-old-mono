package com.gsc.bm.server.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsc.bm.server.model.Card;
import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import lombok.Getter;

import java.util.*;

@Getter
public class Player {

    // TODO builder pattern for this

    // TODO expose public information

    private final String playerId;
    private final Character chosenCharacter;
    private final List<Card> cardsInHand = new ArrayList<>();
    private final Stack<Card> deck = new Stack<>();

    public Player(String id, Character chosenCharacter, List<Card> deck) {
        this.playerId = id;
        this.chosenCharacter = chosenCharacter;

        this.deck.addAll(deck);
        Collections.shuffle(this.deck);
        drawCards(3);
    }

    public void drawCard() {
        if (!deck.isEmpty())
            cardsInHand.add(deck.pop());
    }

    public void drawCards(int howMany) {
        for (int i = 0; i < howMany; i++)
            drawCard();
    }

    public void discardCard(Card card) {
        cardsInHand.remove(card);
    }

    @JsonIgnore
    public Map<Resource, Integer> getResources() {
        return chosenCharacter.getResources();
    }

    public void gainResource(Resource res, int amount) {
        chosenCharacter.getResources().put(res, chosenCharacter.getResources().get(res) + amount);
    }

    public void loseResource(Resource res, int amount) {
        // manage when resource is not already present in map?? should never happen
        chosenCharacter.getResources().put(res, chosenCharacter.getResources().get(res) - amount);
    }

    public boolean isDead() {
        return chosenCharacter.getResources().get(Resource.HEALTH) <= 0;
    }
}
