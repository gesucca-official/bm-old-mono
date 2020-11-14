package com.gsc.bm.server.others;

import com.gsc.bm.server.model.game.*;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class Games {

    private static Games _INSTANCE;

    private Games() {
    }

    public static synchronized Games getInstance() {
        if (_INSTANCE == null)
            _INSTANCE = new Games();
        return _INSTANCE;
    }

    Map<String, Game> games = new HashMap<>();
    Map<String, Set<String>> usersInGames = new HashMap<>();

    public synchronized void addNewGame(Game game) {
        games.put(game.getGameId(), game);
        usersInGames.put(
                game.getGameId(),
                game.getPlayers().values()
                        .stream()
                        .filter(p -> !(p instanceof ComPlayer))
                        .map(Player::getPlayerId)
                        .collect(Collectors.toSet())
        );
        log.info("Created Game " + game.getGameId() + " with users subscribed: " + usersInGames.get(game.getGameId()));
    }

    public synchronized Game getGame(String gameId) {
        return games.get(gameId);
    }

    // TODO if this is here maybe this whole class should create and manage game objects
    public synchronized void userLeaveGame(String gameId, String playerId) {
        usersInGames.get(gameId).remove(playerId);
        if (usersInGames.get(gameId).isEmpty()) {
            usersInGames.remove(gameId);
            games.remove(gameId);
        }
    }

    public synchronized void submitMove(Move move, Runnable callback) throws IllegalMoveException {
        Game game = games.get(move.getGameId());
        game.submitMove(move);

        // is a player is an AI player, automatically submit its move
        for (Player p : game.getPlayers().values())
            if (p instanceof ComPlayer)
                game.submitMove(((ComPlayer) p).chooseMove(game));

        if (game.isReadyToResolveMoves()) {
            game.resolveMoves();
            callback.run();
        }
    }

}
