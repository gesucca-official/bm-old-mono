package com.gsc.bm.server.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Game {

    String gameId;

    Map<String, Player> players;

    public Game(List<Player> players) {
        // TODO invent a proper id assigning strategy
        this.gameId = players.toString() + System.currentTimeMillis();

        // rearrange players in a map
        this.players = new HashMap<>(players.size());
        for (Player p : players)
            this.players.put(p.getPlayerId(), p);
    }
}
