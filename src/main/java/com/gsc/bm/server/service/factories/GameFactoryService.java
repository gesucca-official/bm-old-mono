package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.service.session.model.QueuedPlayer;

import java.util.List;

public interface GameFactoryService {

    Game craftQuick1vComGame(String playerId);

    Game craftQuickMultiPlayerGame(List<QueuedPlayer> players);

}
