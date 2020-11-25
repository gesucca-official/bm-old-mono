package com.gsc.bm.server.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.Card;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Hex;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@ToString
@Log4j2
public class Game implements Serializable {

    private String gameId;
    private final Map<String, Player> players;

    private final List<Move> pendingMoves = new ArrayList<>();

    private final List<Move> resolvedMoves = new ArrayList<>();
    private final Map<String, List<String>> timeBasedEffects = new HashMap<>();

    private int turn = 0;

    @JsonProperty
    public boolean isOver() {
        int dead = 0;
        for (String playerId : players.keySet())
            if (players.get(playerId).getCharacter().isDead())
                dead++;
        return dead >= players.size() - 1;
    }

    @JsonProperty
    public Optional<String> getWinner() {
        if (isOver())
            for (String playerId : players.keySet())
                if (!players.get(playerId).getCharacter().isDead())
                    return Optional.of(players.get(playerId).getPlayerId());
        return Optional.empty();
    }

    public Game(List<Player> players) {
        generateGameId(players);
        // rearrange players in a map
        this.players = new HashMap<>(players.size());
        for (Player p : players)
            this.players.put(p.getPlayerId(), p);
    }

    public void submitMove(Move move) throws IllegalMoveException {
        log.info("Player " + move.getPlayerId() + " submitted this Move: " + move);
        Move.MoveCheckResult moveCheckResult = move.isValidFor(this);
        if (moveCheckResult.isValid())
            pendingMoves.add(move);
        else throw new IllegalMoveException(move.getPlayerId(), moveCheckResult.getComment());
    }

    @JsonIgnore
    public boolean isReadyToResolveMoves() {
        autoSubmitDeadPlayersMove();
        autoSubmitComPlayersMove();
        return !isOver() && pendingMoves.size() == players.size();
    }

    public void resolveMoves(Consumer<Game> callback) {
        if (!isReadyToResolveMoves())
            throw new RuntimeException("Trying to Resolve Moves when Game is not Ready!");

        pendingMoves.removeAll(
                pendingMoves.stream().filter(Move::isVoid).collect(Collectors.toList())
        );

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

        resolvedMoves.clear();
        resolvedMoves.addAll(pendingMoves);
        pendingMoves.clear();

        timeBasedEffects.clear();
        for (String playerId : players.keySet())
            timeBasedEffects.put(playerId, players.get(playerId).getCharacter().resolveTimeBasedEffects());

        log.info("Current Turn has been resolved: " + turn);
        turn++;

        if (!isOver() && isReadyToResolveMoves())
            resolveMoves(callback);
        else callback.accept(this);
    }

    @JsonIgnore
    public List<Player> getOpponents(String playerId) {
        List<Player> opponents = new ArrayList<>(players.size());
        players.values().forEach(p -> {
            if (!p.getPlayerId().equals(playerId) && !p.getCharacter().isDead())
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
                .orElseThrow(() -> {
                    log.info("Player " + playerId + " is trying to get an Illegal Card from Hand: " + cardName);
                    return new IllegalMoveException(playerId, "don't have that card in hand");
                });
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
                        log.info("Successive Move found: " + pendingMoves.get(j));
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

    private void autoSubmitComPlayersMove() {
        // is a player is an AI player, automatically submit its move
        for (Player p : getPlayers().values())
            if (p instanceof ComPlayer && pendingMoves.stream().noneMatch(m -> m.getPlayerId().equals(p.getPlayerId()))) {
                Move comMove = ((ComPlayer) p).chooseMove(this);
                log.info("Player " + p.getPlayerId() + " auto submit Move: " + comMove);
                submitMove(comMove);
            }
    }

    private void autoSubmitDeadPlayersMove() {
        // dead players do nothing by default
        players.values().forEach(p -> {
            if (p.getCharacter().isDead() && pendingMoves.stream().noneMatch(m -> m.getPlayerId().equals(p.getPlayerId()))) {
                log.info("Player " + p.getPlayerId() + " is dead and is auto submitting an Empty Move");
                submitMove(Move.voidMove(p.getPlayerId()));
            }
        });
    }
}
