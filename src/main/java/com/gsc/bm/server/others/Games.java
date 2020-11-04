package com.gsc.bm.server.others;

import com.gsc.bm.server.model.game.*;

import java.util.HashMap;
import java.util.Map;

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

    public synchronized void addNewGame(Game game) {
        games.put(game.getGameId(), game);
    }

    public synchronized Game getGame(String gameId) {
        return games.get(gameId);
    }

    public synchronized void destroyGame(String gameId) {
        games.remove(gameId);
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
