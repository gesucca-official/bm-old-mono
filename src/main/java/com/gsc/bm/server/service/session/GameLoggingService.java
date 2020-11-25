package com.gsc.bm.server.service.session;

import com.gsc.bm.server.service.session.model.ActionLog;
import com.gsc.bm.server.view.SlimGameView;

import java.io.Serializable;

public interface GameLoggingService extends Serializable {

    void log(SlimGameView game, String status, ActionLog actionLog);

    void flush(SlimGameView game);
}
