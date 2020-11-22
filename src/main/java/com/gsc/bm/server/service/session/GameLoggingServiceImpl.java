package com.gsc.bm.server.service.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Player;
import com.gsc.bm.server.repo.external.GameLogRecord;
import com.gsc.bm.server.repo.external.GameLogRepository;
import com.gsc.bm.server.service.session.model.ActionLog;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GameLoggingServiceImpl implements GameLoggingService {

    private static final Map<String, List<ActionLog<?>>> _GAMES_LOG = new ConcurrentHashMap<>();

    private final GameLogRepository repo;

    public GameLoggingServiceImpl(GameLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public <T> void log(String gameId, ActionLog<T> actionLog) {
        _GAMES_LOG.computeIfAbsent(gameId, k -> new LinkedList<>());
        _GAMES_LOG.get(gameId).add(actionLog);
    }

    @Override
    public void saveAndFlush(Game game) {
        String json;
        try {
            json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(_GAMES_LOG.get(game.getGameId()));
        } catch (JsonProcessingException e) {
            json = "Error serializing LOG to JSON";
            e.printStackTrace();
        }
        repo.save(new GameLogRecord(
                game.getGameId(),
                game.getPlayers().values()
                        .stream()
                        .map(Player::getPlayerId)
                        .reduce((p1, p2) -> p1 + ", " + p2)
                        .orElse("Error reducing PlayerIds"),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                json
        ));
    }
}
