package com.gsc.bm.server.service.session;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.service.session.model.ActionLog;

import java.io.Serializable;

public interface GameLoggingService extends Serializable {

    String STARTED = "STARTED";
    String IN_PROGRESS = "IN_PROGRESS";
    String ENDED = "ENDED";

    void log(Game game, String status, ActionLog actionLog);

    void flush(Game game);
}
