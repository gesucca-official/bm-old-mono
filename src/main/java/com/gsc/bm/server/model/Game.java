package com.gsc.bm.server.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Game {

    String gameId;

    Map<String, Player> players;

    List<Move> lastResolvedMoves = new ArrayList<>();

    public Game(List<Player> players) {
        // TODO invent a proper id assigning strategy
        this.gameId = players.toString() + System.currentTimeMillis();

        // rearrange players in a map
        this.players = new HashMap<>(players.size());
        for (Player p : players)
            this.players.put(p.getPlayerId(), p);

        // lastResolvedMoves.add(new Move("card", "player1", "player2", this.gameId));
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
}
