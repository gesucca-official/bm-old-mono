package com.gsc.bm.server.service.view;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.service.view.model.PlayerGameView;
import com.gsc.bm.server.service.view.model.SlimGameView;

public interface ViewExtractorService {

    SlimGameView extractGlobalSlimView(Game game);

    PlayerGameView extractViewFor(Game game, String playerId);

}
