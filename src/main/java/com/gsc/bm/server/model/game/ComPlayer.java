package com.gsc.bm.server.model.game;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.Card;

import java.util.*;
import java.util.stream.Collectors;

public class ComPlayer extends Player {

    // for now this fabulous AI is just random choices

    public ComPlayer(Character character, List<Card> characterBoundCards, Card lastResortCard, List<Card> deck) {
        super("ComPlayer_" + (int) (Math.random() * 10000), character, characterBoundCards, lastResortCard, deck);
    }

    public Move chooseMove(Game game) {
        List<Card> availableCards = new ArrayList<>(getCardsInHand());
        Collections.shuffle(availableCards);

        for (int i = availableCards.size() - 1; i >= 0; i--) {
            Move chosenMove = craftMoveByIndex(game, availableCards, i, choseTarget(game, availableCards.get(i)), game.getGameId());
            if (chosenMove.isValidFor(game).isValid())
                return chosenMove;
        }

        // if no move in hand are valid, return void move
        return Move.voidMove(getPlayerId());
    }

    private String choseTarget(Game game, Card chosenCard) {
        List<String> availableTargets = game.getOpponents(getPlayerId())
                .stream()
                .map(Player::getPlayerId)
                .collect(Collectors.toList());

        if (chosenCard.getCanTarget().contains(Card.CardTarget.SELF))
            availableTargets.add("SELF");

        Collections.shuffle(availableTargets);
        return availableTargets.get(0);
    }

    private Move craftMoveByIndex(Game game, List<Card> cards, int index, String target, String gameId) {
        Map<Move.AdditionalAction, String> choices = new HashMap<>();
        if (cards.get(index).isCharacterBound())
            choices.put(Move.AdditionalAction.DISCARD_ONE, chooseDiscard(cards));
        if (cards.get(index).getCanTarget().contains(Card.CardTarget.NEAR_ITEM))
            choices.put(Move.AdditionalAction.TARGET_ITEM, choseNearItem());
        else if (cards.get(index).getCanTarget().contains(Card.CardTarget.FAR_ITEM))
            choices.put(Move.AdditionalAction.TARGET_ITEM, choseFarItem(game));

        return new Move(
                cards.get(index).getName(),
                getPlayerId(),
                target,
                gameId,
                choices
        );
    }

    private String choseFarItem(Game game) {
        List<String> items = game.getOpponents(getPlayerId())
                .stream()
                .flatMap(player -> player.getCharacter().getItems().stream())
                .map(Card::getName)
                .collect(Collectors.toList());
        Collections.shuffle(items);
        return items.size() > 0 ? items.get(0) : null;
    }

    private String choseNearItem() {
        List<Card> items = new ArrayList<>(getCharacter().getItems());
        Collections.shuffle(items);
        return items.size() > 0 ? items.get(0).getName() : null;
    }

    private String chooseDiscard(List<Card> cards) {
        Collections.shuffle(cards);
        for (Card c : cards)
            if (!c.isCharacterBound())
                return c.getName();
        return null;
    }

}
