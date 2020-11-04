package com.gsc.bm.server.model.game;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.Card;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ComPlayer extends Player {

    public ComPlayer(Character character, List<Card> deck) {
        super("ComPlayer_" + (int) (Math.random() * 10000), character, deck);
    }

    public Move chooseMove(Game game) {
        // for now this fabulous AI is just random choice
        List<String> availableCards = getCardsInHand().stream().map(Card::getName).collect(Collectors.toList());
        Collections.shuffle(availableCards);
        List<String> availableTargets = game.getOpponents(getPlayerId()).stream().map(Player::getPlayerId).collect(Collectors.toList());
        Collections.shuffle(availableTargets);

        int cardIndex = availableCards.size() - 1;
        // TODO infinite loop if nothing is valid!!! make a dummy move when nothing could be done?
        while (!craftMoveByIndex(availableCards, cardIndex, availableTargets.get(0), game.getGameId()).isValidFor(game).isValid()) {
            cardIndex--;
        }
        return craftMoveByIndex(availableCards, cardIndex, availableTargets.get(0), game.getGameId());
    }

    private Move craftMoveByIndex(List<String> cards, int index, String target, String gameId) {
        return new Move(
                cards.get(index),
                getPlayerId(),
                target,
                gameId
        );
    }

}
