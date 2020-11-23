package com.gsc.bm.server.service.session;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.service.session.model.ActionLog;

public interface GameLoggingService {

    void log(String gameId, ActionLog actionLog);

    void flush(Game game);
}
