package com.gsc.bm.server.service;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameFactoryServiceImpl implements GameFactoryService {

    private final PlayerFactoryService playerFactoryService;

    @Autowired
    public GameFactoryServiceImpl(PlayerFactoryService playerFactoryService) {
        this.playerFactoryService = playerFactoryService;
    }

    @Override
    public Game craftQuick1vComGame(String playerId) {
        return new Game(
                List.of(
                        playerFactoryService.craftRandomPlayer(playerId),
                        playerFactoryService.craftRandomComPlayer()
                ));
    }

    @Override
    public Game craftQuick4ffaComGame(String playerId) {
        List<Player> players = new ArrayList<>(4);
        players.add(playerFactoryService.craftRandomPlayer(playerId));
        players.add(playerFactoryService.craftRandomComPlayer());
        players.add(playerFactoryService.craftRandomComPlayer());
        players.add(playerFactoryService.craftRandomComPlayer());
        return new Game(players);
    }

    @Override
    public Game craftQuickMultiPlayerGame(List<String> playerIds) {
        System.out.println(playerIds.stream().map(playerFactoryService::craftRandomPlayer).collect(Collectors.toList()));
        return new Game(
                playerIds.stream().map(playerFactoryService::craftRandomPlayer).collect(Collectors.toList())
        );
    }

}
