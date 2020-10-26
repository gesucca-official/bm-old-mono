package com.gsc.bm.server.model.game;

import com.gsc.bm.server.model.Card;
import com.gsc.bm.server.model.Character;
import lombok.Getter;

import java.util.List;

@Getter
public class Player {

    // TODO builder pattern for this

    //TODO expose public information

    private final String playerId;
    private final Character chosenCharacter;
    private final List<Card> cardsInHand;
    private final List<Card> deck;

    public Player(String id, Character chosenCharacter, List<Card> deck) {
        this.playerId = id;
        this.chosenCharacter = chosenCharacter;
        this.deck = deck;
        this.cardsInHand = deck; // for now there's no deck or drawing cards
    }

    // stuff like this can be added later here for Game commodity
    public boolean isDead() {
        return chosenCharacter.isDead();
    }
}
