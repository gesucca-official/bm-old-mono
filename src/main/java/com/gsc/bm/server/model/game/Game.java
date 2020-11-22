package com.gsc.bm.server.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.cards.Card;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Getter
@ToString
@Log4j2
public class Game implements Serializable {

    //TODO extract an interface out of this so the controllers depends on it and not on this implementation

    private String gameId;

    private final Map<String, Player> players;

    private final List<Move> pendingMoves = new ArrayList<>();
    private final List<Move> lastResolvedMoves = new ArrayList<>();
    private final Map<String, List<String>> lastResolvedTimeBasedEffects = new HashMap<>();

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
                        m -> {
                            Move move = (Move) m; // I am not sure why I have to downcast here
                            return -getCardFromHand(move.getPlayerId(), move.getPlayedCardName()).getPriority();
                        }
                ).thenComparingInt(
                        m -> -getSelf((Move) m).getCharacter().getResources().get(Resource.ALERTNESS)
                ));

        for (Move m : pendingMoves) {
            m.applyEffectTo(this);
            getSelf(m).discardCard(getCardFromHand(m.getPlayerId(), m.getPlayedCardName()));
            getSelf(m).drawCard();
        }
        prepareForNextTurn();
    }

    @JsonIgnore
    public Game getSlimGlobalView() {
        // this way it should make a clone
        byte[] bytes = SerializationUtils.serialize(this);
        Game gameClone = (Game) SerializationUtils.deserialize(bytes);

        // this serves only to save text on the game log, I am not sure this is a good idea
        assert gameClone != null;
        for (Player p : gameClone.getPlayers().values()) {
            for (Card c : p.getDeck())
                ((AbstractCard) c).setGuiEffectDescription(null);
            for (Card c : p.getCardsInHand())
                ((AbstractCard) c).setGuiEffectDescription(null);
        }
        return gameClone;
    }

    @JsonIgnore
    public Game getViewFor(String playerId) {
        byte[] bytes = SerializationUtils.serialize(this);
        Game gameViewForPlayer = (Game) SerializationUtils.deserialize(bytes);

        assert gameViewForPlayer != null;
        for (Player p : gameViewForPlayer.getPlayers().values()) {
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
    public Card getCardFromHand(String playerId, String cardName) {
        // getting this card also checks if it is in that player's hand adn throws exception accordingly
        return players.get(playerId).getCardsInHand().stream()
                .filter(c -> c.getName().equalsIgnoreCase(cardName))
                .findAny()
                .orElseThrow(() -> new IllegalMoveException(playerId, "don't have that card in hand"));
    }

    @JsonIgnore
    public Player getSelf(Move move) {
        return players.get(move.getPlayerId());
    }

    @JsonIgnore
    public Player getTarget(Move move) {
        if (move.getTargetId().equalsIgnoreCase("SELF"))
            return players.get(move.getPlayerId());
        else
            return players.get(move.getTargetId());
    }

    @JsonIgnore
    public Optional<Move> getPendingMoveOfTargetIfMovesAfterPlayer(String playerId, String targetId) {
        log.info("Player " + playerId + "'s Move has queried for the Successive Move of " + targetId);
        // this is called by cards when they resolves, so pending moves are already sorted in resolution order
        for (int i = 0; i < pendingMoves.size(); i++) {
            if (pendingMoves.get(i).getPlayerId().equals(playerId))
                for (int j = i + 1; j < pendingMoves.size(); j++)
                    if (pendingMoves.get(j).getPlayerId().equals(targetId)) {
                        log.info("Successive Move found!");
                        return Optional.of(pendingMoves.get(j));
                    }
            // those nested cycles, those indexes... god that smells bad
        }
        log.info("Found nothing...");
        return Optional.empty();
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
        lastResolvedTimeBasedEffects.clear();
        for (String playerId : players.keySet())
            lastResolvedTimeBasedEffects.put(playerId, players.get(playerId).getCharacter().resolveTimeBasedEffects());
        lastResolvedMoves.clear();
        lastResolvedMoves.addAll(pendingMoves);
        pendingMoves.clear();
    }
}
