package com.gsc.bm.server.service.view;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.service.view.model.client.ClientGameView;
import com.gsc.bm.server.service.view.model.logging.SlimGameView;

public interface ViewExtractorService {

    SlimGameView extractGlobalSlimView(Game game);

    ClientGameView extractViewFor(Game game, String playerId);

}
