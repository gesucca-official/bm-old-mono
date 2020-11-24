package com.gsc.bm.server.service.session;

import com.gsc.bm.server.repo.external.GameLogRecord;
import com.gsc.bm.server.repo.external.GameLogRepository;
import com.gsc.bm.server.service.session.model.ActionLog;
import com.gsc.bm.server.view.SlimGameView;
import com.gsc.bm.server.view.SlimPlayerView;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Repository
public class GameLoggingServiceImpl implements GameLoggingService {

    private static final Map<String, List<ActionLog>> GAMES_LOG = new ConcurrentHashMap<>();
    private static final ExecutorService SAVE_QUEUE = Executors.newSingleThreadExecutor();

    private final GameLogRepository repo;

    public GameLoggingServiceImpl(GameLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public void log(SlimGameView game, String status, ActionLog actionLog) {
        GAMES_LOG.computeIfAbsent(game.getGameId(), k -> new LinkedList<>());
        GAMES_LOG.get(game.getGameId()).add(actionLog);
        updateLogTable(game, status, GAMES_LOG.get(game.getGameId()));
    }

    @Override
    public void flush(SlimGameView game) {
        updateLogTable(game, "ENDED", GAMES_LOG.get(game.getGameId()));
        GAMES_LOG.remove(game.getGameId());
    }

    private void updateLogTable(SlimGameView game, String status, List<ActionLog> log) {
        String players = game.getPlayers()
                .stream()
                .map(SlimPlayerView::getPlayerId)
                .reduce((p1, p2) -> p1 + ", " + p2)
                .orElse("Error reducing PlayerIds");
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        SAVE_QUEUE.submit(() -> repo.save(new GameLogRecord(game.getGameId(), players, date, status, log)));
    }

}
