package com.gsc.bm.server.stomp;

import com.gsc.bm.server.model.Game;
import com.gsc.bm.server.model.Player;
import com.gsc.bm.server.service.PlayerFactoryService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    private final PlayerFactoryService playerFactoryService;

    // TODO for now a single queue will do
    private List<Player> playersQueue = new ArrayList<>();

    private final List<Game> games = new ArrayList<>();

    public GameController(PlayerFactoryService playerFactoryService) {
        this.playerFactoryService = playerFactoryService;
    }

    @MessageMapping("/game/join")
    @SendTo("/topic/game/update")
    public Game joinGame(String playerId) throws InterruptedException {
        playersQueue.add(playerFactoryService.craftRandomPlayer(playerId));
        while (true) {
            if (playersQueue.size() >= 2) {
                Game game = new Game(playersQueue);
                playersQueue = new ArrayList<>();
                games.add(game);
                return game;
            }
            // TODO find a way to catch the event instead of busy waiting
            Thread.sleep(1000);
        }
    }

    @MessageMapping("/game/move")
    @SendTo("/topic/game/update")
    public Game makeYourMove(String cardPlayed) {
        return games.stream().findAny().orElse(null);
    }
}
