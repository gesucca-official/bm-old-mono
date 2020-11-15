package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.game.Game;

import java.util.List;

public interface GameFactoryService {

    Game craftQuick1vComGame(String playerId);

    Game craftQuick4ffaComGame(String playerId);

    Game craftQuickMultiPlayerGame(List<String> playerIds);

}
