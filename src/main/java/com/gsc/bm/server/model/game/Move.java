package com.gsc.bm.server.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class Move implements Serializable {

    @JsonIgnore
    public static Move EMPTY = new Move();

    private final String playedCardName;
    private final String playerId;
    private final String targetId;
    private final String gameId;

    private final boolean isEmpty;

    private List<String> moveEffectToSelf;
    private List<String> moveEffectToTarget;

    @JsonCreator
    public Move(String playedCardName, String playerId, String targetId, String gameId) {
        this.playedCardName = playedCardName;
        this.playerId = playerId;
        this.targetId = targetId;
        this.gameId = gameId;
        this.isEmpty = false;
    }

    private Move() {
        this.playedCardName = null;
        this.playerId = null;
        this.targetId = null;
        this.gameId = null;
        this.isEmpty = true;
    }

    @AllArgsConstructor
    @Getter
    static class MoveCheckResult {
        boolean valid;
        String comment;
    }

    @JsonIgnore
    public MoveCheckResult isValidFor(Game game) {
        if (this.isEmpty)
            return new MoveCheckResult(true, "is empty and you do nothing");

        // check is player has already submitted a move
        if (game.getPendingMoves().stream().anyMatch(m -> m.getPlayerId().equalsIgnoreCase(playerId)))
            return new MoveCheckResult(false, "already submitted a move");
        // getting this card also checks if it is in that player's hand
        Card playedCard = game.getPlayedCardFromHand(this);
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
        if (this.isEmpty)
            return;
        game.getPlayedCardFromHand(this).getCost().forEach(
                (key, value) -> game.getSelf(this).getCharacter().loseResource(key, value)
        );
    }

    @JsonIgnore
    public void applyEffectTo(Game game) {
        if (this.isEmpty)
            return;
        Card.CardResolutionReport report = game.getPlayedCardFromHand(this).resolve(game, this);
        moveEffectToSelf = report.getSelfReport();
        moveEffectToTarget = report.getTargetReport();
    }

}
