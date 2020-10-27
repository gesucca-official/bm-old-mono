package com.gsc.bm.server.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsc.bm.server.model.Card;
import com.gsc.bm.server.model.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class Move {

    private final String playedCardName;
    private final String playerId;
    private final String targetId;
    private final String gameId;

    @AllArgsConstructor
    @Getter
    static class MoveCheckResult {
        boolean valid;
        String comment;
    }

    @JsonIgnore
    public MoveCheckResult isValidFor(Game game) {
        // check is player has already submitted a move
        if (game.getPendingMoves().stream().anyMatch(m -> m.getPlayerId().equalsIgnoreCase(playerId)))
            return new MoveCheckResult(false, "already submitted a move");
        // getting this card also checks if it is in that player's hand
        Card playedCard = game.getPlayedCardFromHand(this);
        Map<Resource, Integer> playerResources = game.getPlayers().get(playerId).getChosenCharacter().getResources();

        // check if costs can be satisfied
        for (Map.Entry<Resource, Integer> cost : playedCard.getCost().entrySet()) {
            int availableResource = playerResources.get(cost.getKey());
            if (availableResource < cost.getValue())
                new MoveCheckResult(false, "cant be cast with current resources");
        }
        return new MoveCheckResult(true, "good, cast it");
    }

    @JsonIgnore
    public void applyCostTo(Game game) {
        game.getPlayedCardFromHand(this).getCost().forEach(
                (key, value) -> game.getSelf(this).loseResource(key, value)
        );
    }

    @JsonIgnore
    public void applyEffectTo(Game game) {
        game.getPlayedCardFromHand(this).resolve(game, this);
    }

}
