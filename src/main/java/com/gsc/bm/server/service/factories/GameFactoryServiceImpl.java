package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Player;
import com.gsc.bm.server.service.session.model.QueuedPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GameFactoryServiceImpl implements GameFactoryService {

    private final Environment env;
    private final PlayerFactoryService playerFactoryService;

    @Autowired
    public GameFactoryServiceImpl(Environment env,
                                  PlayerFactoryService playerFactoryService) {
        this.env = env;
        this.playerFactoryService = playerFactoryService;
    }

    @Override
    public Game craftQuick1vComGame(String playerId) {
        return new Game(List.of(
                playerFactoryService.craftRandomPlayer(playerId),
                playerFactoryService.craftRandomComPlayer()
        ), Objects.equals(env.getProperty("log.db"), "N"));
    }

    @Override
    public Game craftQuickMultiPlayerGame(List<QueuedPlayer> queuedPlayers) {
        List<Player> players = new ArrayList<>(queuedPlayers.size());
        for (QueuedPlayer qp : queuedPlayers)
            players.add(
                    qp.isHuman()
                            ? playerFactoryService.craftRandomPlayer(qp.getPlayerId())
                            : playerFactoryService.craftRandomComPlayer()
            );
        return new Game(players, Objects.equals(env.getProperty("log.db"), "N"));
    }

}
