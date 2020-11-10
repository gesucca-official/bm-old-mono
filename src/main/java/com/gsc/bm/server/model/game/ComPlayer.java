package com.gsc.bm.server.model.game;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ComPlayer extends Player {

    // TODO refactor this

    public ComPlayer(Character character, List<Card> deck) {
        super("ComPlayer_" + (int) (Math.random() * 10000), character, deck);
    }

    public Move chooseMove(Game game) {
        if (game.getPlayers().get(getPlayerId()).getCharacter().isDead())
            return Move.EMPTY;

        // for now this fabulous AI is just random choice
        List<Card> availableCards = new ArrayList<>(getCardsInHand());
        Collections.shuffle(availableCards);
        List<String> availableTargets = game.getOpponents(getPlayerId())
                .stream()
                .filter(p -> !p.getCharacter().isDead())
                .map(Player::getPlayerId)
                .collect(Collectors.toList());
        Collections.shuffle(availableTargets);

        int cardIndex = availableCards.size() - 1;
        // TODO infinite loop if nothing is valid!!! make a dummy move when nothing could be done?
        while (!craftMoveByIndex(availableCards, cardIndex, choseTarget(game, availableCards.get(cardIndex)), game.getGameId()).isValidFor(game).isValid()) {
            cardIndex--;
        }
        return craftMoveByIndex(availableCards, cardIndex, choseTarget(game, availableCards.get(cardIndex)), game.getGameId());
    }

    private String choseTarget(Game game, Card chosenCard) {
        if (chosenCard.getCanTarget().contains(Card.CardTarget.SELF))
            return "SELF";
        List<String> availableTargets = game.getOpponents(getPlayerId()).stream().map(Player::getPlayerId).collect(Collectors.toList());
        Collections.shuffle(availableTargets);
        return availableTargets.get(0);
    }

    private Move craftMoveByIndex(List<Card> cards, int index, String target, String gameId) {
        return new Move(
                cards.get(index).getName(),
                getPlayerId(),
                target,
                gameId
        );
    }

}
