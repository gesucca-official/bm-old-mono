package com.gsc.bm.server.service.session;

import java.util.List;
import java.util.Optional;

public interface QueueService {

    Optional<List<String>> join1v1Queue(String playerId);

    Optional<List<String>> join4ffaQueue(String playerId);
}
