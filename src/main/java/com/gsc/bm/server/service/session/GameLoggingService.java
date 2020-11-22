package com.gsc.bm.server.service.session;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.service.session.model.ActionLog;

public interface GameLoggingService {

    <T> void log(String gameId, ActionLog<T> actionLog);

    void saveAndFlush(Game game);
}
