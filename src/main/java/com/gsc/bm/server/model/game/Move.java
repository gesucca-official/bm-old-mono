package com.gsc.bm.server.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class Move implements Serializable {

    public enum AdditionalAction {
        DISCARD_ONE
    }

    @JsonIgnore
    public static Move EMPTY = new Move();

    private final String playedCardName;
    private final String playerId;
    private final String targetId;
    private final String gameId;

    private final Map<AdditionalAction, String> choices;

    @Setter
    private boolean isVoid;

    private List<String> moveEffectToSelf;
    private List<String> moveEffectToTarget;

    // TODO consider builder pattern for this? dunno the interaction with jackson
    @JsonCreator
    public Move(String playedCardName,
                String playerId,
                String targetId,
                String gameId,
                Map<AdditionalAction, String> choices) {
        this.playedCardName = playedCardName;
        this.playerId = playerId;
        this.targetId = targetId;
        this.gameId = gameId;
        this.choices = choices;
        this.isVoid = false;
    }

    private Move() {
        this.playedCardName = null;
        this.playerId = null;
        this.targetId = null;
        this.gameId = null;
        this.choices = null;
        this.isVoid = true;
    }

    @AllArgsConstructor
    @Getter
    static class MoveCheckResult {
        boolean valid;
        String comment;
    }

    @JsonIgnore
    public MoveCheckResult isValidFor(Game game) {
        if (this.isVoid)
            return new MoveCheckResult(true, "is empty and you do nothing");

        if (game.getCardFromHand(playerId, playedCardName).isCharacterBound()) {
            if (choices == null || choices.isEmpty())
                return new MoveCheckResult(false, "character bound cards should discard something else");
            Card toBeDiscarded = game.getCardFromHand(playerId, choices.get(AdditionalAction.DISCARD_ONE));
            if (toBeDiscarded.isCharacterBound())
                throw new IllegalMoveException(game.getSelf(this).getPlayerId(), "can't discard character bound card");
        }

        // check is player has already submitted a move
        if (game.getPendingMoves().stream().anyMatch(m -> m.getPlayerId().equalsIgnoreCase(playerId)))
            return new MoveCheckResult(false, "already submitted a move");
        // getting this card also checks if it is in that player's hand
        Card playedCard = game.getCardFromHand(playerId, playedCardName);
        Map<Resource, Integer> playerResources = game.getPlayers().get(playerId).getCharacter().getResources();

        // check if costs can be satisfied
        for (Map.Entry<Resource, Integer> cost : playedCard.getCost().entrySet()) {
            int availableResource = playerResources.get(cost.getKey());
            if (availableResource < cost.getValue())
                return new MoveCheckResult(false, "cant be cast with current resources");
        }
        return new MoveCheckResult(true, "good, cast it");
    }

    @JsonIgnore
    public void applyCostTo(Game game) {
        if (this.isVoid)
            return;
        game.getCardFromHand(playerId, playedCardName).getCost().forEach(
                (key, value) -> game.getSelf(this).getCharacter().loseResource(key, value)
        );
    }

    @JsonIgnore
    public void applyEffectTo(Game game) {
        if (this.isVoid) {
            moveEffectToSelf = List.of("Didn't do anything!");
            return;
        }
        Card.CardResolutionReport report = game.getCardFromHand(playerId, playedCardName).resolve(game, this);
        moveEffectToSelf = report.getSelfReport();
        moveEffectToTarget = report.getTargetReport();
    }

}
