package com.gsc.bm.server.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Queues {

    private static Queues _INSTANCE;

    private Queues() {
    }

    public static synchronized Queues getInstance() {
        if (_INSTANCE == null)
            _INSTANCE = new Queues();
        return _INSTANCE;
    }

    private final List<String> queue1v1 = new ArrayList<>(2);
    private final List<String> queue4ffa = new ArrayList<>(4);

    public synchronized Optional<List<String>> join1v1Queue(String playerId) {
        queue1v1.add(playerId);
        if (queue1v1.size() >= 2) {
            List<String> playersInGame = new ArrayList<>(queue1v1);
            queue1v1.clear();
            return Optional.of(playersInGame);
        }
        return Optional.empty();
    }

    public synchronized Optional<List<String>> join4ffaQueue(String playerId) {
        queue4ffa.add(playerId);
        if (queue4ffa.size() >= 4) {
            List<String> playersInGame = new ArrayList<>(queue4ffa);
            queue4ffa.clear();
            return Optional.of(playersInGame);
        }
        return Optional.empty();
    }

}
