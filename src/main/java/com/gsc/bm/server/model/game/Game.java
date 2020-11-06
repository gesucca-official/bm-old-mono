package com.gsc.bm.server.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.Card;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Getter
@ToString
public class Game implements Serializable {

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

    @JsonProperty
    public boolean isOver() {
        int dead = 0;
        for (String playerId : players.keySet())
            if (players.get(playerId).getCharacter().isDead())
                dead++;
        return dead == players.size() - 1;
    }

    @JsonProperty
    public String getWinner() {
        if (isOver())
            for (String playerId : players.keySet())
                if (!players.get(playerId).getCharacter().isDead())
                    return players.get(playerId).getPlayerId();
        return null;
    }

    public void submitMove(Move move) throws IllegalMoveException {
        Move.MoveCheckResult moveCheckResult = move.isValidFor(this);
        if (moveCheckResult.isValid())
            pendingMoves.add(move);
        else throw new IllegalMoveException(move.getPlayerId(), moveCheckResult.getComment());
    }

    public boolean isReadyToResolveMoves() {
        return pendingMoves.size() == players.size();
    }

    public void resolveMoves() {
        for (Move m : pendingMoves)
            m.applyCostTo(this);

        pendingMoves.sort(
                Comparator.comparingInt(
                        m -> -getPlayedCardFromHand((Move) m).getPriority() // I am not sure why I have to downcast here
                ).thenComparingInt(
                        m -> -getSelf((Move) m).getCharacter().getResources().get(Resource.ALERTNESS)
                ));

        for (Move m : pendingMoves) {
            m.applyEffectTo(this);
            getSelf(m).discardCard(getPlayedCardFromHand(m));
            getSelf(m).drawCard();
        }
        prepareForNextTurn();
    }

    @JsonIgnore
    public Game getViewFor(String playerId) {
        // this way it should make a clone
        byte[] bytes = SerializationUtils.serialize(this);
        Game gameViewForPlayer = (Game) SerializationUtils.deserialize(bytes);

        for (Player p : gameViewForPlayer.getPlayers().values()) {
            p.getDeck().clear();
            if (!p.getPlayerId().equals(playerId))
                p.getCardsInHand().clear();
        }
        return gameViewForPlayer;
    }

    @JsonIgnore
    public List<Player> getOpponents(String playerId) {
        List<Player> opponents = new ArrayList<>(players.size());
        players.values().forEach(p -> {
            if (!p.getPlayerId().equals(playerId))
                opponents.add(p);
        });
        return opponents;
    }

    @JsonIgnore
    public Card getPlayedCardFromHand(Move move) {
        // getting this card also checks if it is in that player's hand adn throws exception accordingly
        return players.get(move.getPlayerId()).getCardsInHand().stream()
                .filter(c -> c.getName().equalsIgnoreCase(move.getPlayedCardName()))
                .findAny()
                .orElseThrow(() -> new IllegalMoveException(move.getPlayerId(), "don't have that card in hand"));
    }

    @JsonIgnore
    public Player getSelf(Move move) {
        return players.get(move.getPlayerId());
    }

    @JsonIgnore
    public Player getTarget(Move move) {
        return players.get(move.getTargetId());
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

    private void prepareForNextTurn() {
        for (String playerId : players.keySet())
            players.get(playerId).getCharacter().resolveTimeBasedEffects();

        lastResolvedMoves.clear();
        lastResolvedMoves.addAll(pendingMoves);
        pendingMoves.clear();
    }
}
