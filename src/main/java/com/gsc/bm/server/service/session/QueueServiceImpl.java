package com.gsc.bm.server.service.session;

import com.gsc.bm.server.service.session.model.UserSessionInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Log4j2
public class QueueServiceImpl implements QueueService {

    // TODO could we make a single queue and let players choose when to stop waiting?
    // maybe 4ffa could become ffa queue with max 4 or 5? and add ability to add com players at will

    private static final Queue<String> queue1v1 = new ConcurrentLinkedQueue<>();
    private static final Queue<String> queue4ffa = new ConcurrentLinkedQueue<>();

    private final ConnectionsService connectionsService;

    @Autowired
    public QueueServiceImpl(ConnectionsService connectionsService) {
        this.connectionsService = connectionsService;
    }

    @Override
    public Optional<List<String>> join1v1Queue(String playerId) {
        log.info(playerId + " joined the 1v1 queue");
        queue1v1.add(playerId);
        connectionsService.changeUserActivity(playerId, UserSessionInfo.Activity.QUEUED);
        if (queue1v1.size() >= 2) {
            List<String> playersInGame = new ArrayList<>(queue1v1);
            queue1v1.clear();
            log.info("1v1 queue full, flushing players " + playersInGame + " to game!");
            return Optional.of(playersInGame);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<String>> join4ffaQueue(String playerId) {
        log.info(playerId + " joined the 4ffa queue");
        queue4ffa.add(playerId);
        connectionsService.changeUserActivity(playerId, UserSessionInfo.Activity.QUEUED);
        if (queue4ffa.size() >= 4) {
            List<String> playersInGame = new ArrayList<>(queue4ffa);
            queue4ffa.clear();
            log.info("4ffa queue full, flushing players " + playersInGame + " to game!");
            return Optional.of(playersInGame);
        }
        return Optional.empty();
    }
}
