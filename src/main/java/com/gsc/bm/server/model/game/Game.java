package com.gsc.bm.server.model.game;

import com.gsc.bm.server.model.Card;
import com.gsc.bm.server.model.Resource;
import lombok.Getter;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Game {

    //TODO extract an interface out of this so the controllers depends on it and not on this implementation

    private String gameId;

    private final Map<String, Player> players;

    private final List<Move> pendingMoves = new ArrayList<>();
    private final List<Move> lastResolvedMoves = new ArrayList<>();

    public Game(List<Player> players) {
        generateGameId(players);
        // rearrange players in a map
        this.players = new HashMap<>(players.size());
        for (Player p : players)
            this.players.put(p.getPlayerId(), p);
    }

    public void submitMove(Move move) throws IllegalMoveException {
        if (!isMoveValid(move)) // this checking method also throws IllegalMoveException
            throw new IllegalMoveException(move.getPlayerId());
        pendingMoves.add(move);
    }

    public boolean isReadyToResolveMoves() {
        return pendingMoves.size() == players.size();
    }

    public void resolveMoves() {
        lastResolvedMoves.clear();
        lastResolvedMoves.addAll(pendingMoves);
        pendingMoves.clear();
    }

    public boolean isOver() {
        // TODO this only works for single player
        for (String playerId : players.keySet())
            if (players.get(playerId).isDead())
                return true;
        return false;
    }

    public String getWinner() {
        // TODO this only works for single player
        if (isOver())
            for (String playerId : players.keySet())
                if (!players.get(playerId).isDead())
                    return players.get(playerId).getPlayerId();
        return null;
    }

    private void generateGameId(List<Player> players) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashBytes = digest.digest(
                    (players.toString() + System.currentTimeMillis())
                            .getBytes(StandardCharsets.UTF_8));
            this.gameId = Hex.encodeHexString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            this.gameId = players.toString() + System.currentTimeMillis();
        }
    }

    // TODO adopt IOC and move this on move class??
    private boolean isMoveValid(Move move) throws IllegalMoveException {
        // check is player has already submit a move
        if (pendingMoves.stream().anyMatch(m -> m.getPlayerId().equalsIgnoreCase(move.getPlayerId())))
            throw new IllegalMoveException(move.getPlayerId());

        // getting this card also checks if it is in that player's hand
        Card playedCard = players.get(move.getPlayerId()).getCardsInHand().stream()
                .filter(c -> c.getName().equalsIgnoreCase(move.getPlayedCardName()))
                .findAny()
                .orElseThrow(() -> new IllegalMoveException(move.getPlayerId()));
        Map<Resource, Integer> playerResources = players.get(move.getPlayerId()).getChosenCharacter().getResources();

        // check if costs can be satisfied
        for (Map.Entry<Resource, Integer> cost : playedCard.getCost().entrySet()) {
            int availableResource = playerResources.get(cost.getKey());
            if (availableResource < cost.getValue())
                return false;
        }
        return true;
    }
}
