package com.gsc.bm.server.stomp;

import com.gsc.bm.server.model.Game;
import com.gsc.bm.server.model.Move;
import com.gsc.bm.server.model.Player;
import com.gsc.bm.server.service.PlayerFactoryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
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
        if (playersQueue.size() >= 2) {
            Game game = new Game(playersQueue);
            playersQueue = new ArrayList<>();
            games.add(game);
            return game;
        } else return null;
    }

    @MessageMapping("/game/move")
    @SendTo("/topic/game/update")
    public Game makeYourMove(Move cardPlayed) throws RuntimeException {
        // return games.get(0);
        // TODO this has to be managed better (and AT ALL)
        throw new RuntimeException(cardPlayed.getPlayerId());
        //return null;
    }

@AllArgsConstructor
@Getter
@ToString
private static class IllegalMove {
    String playerId;
    String message;
}

    @MessageExceptionHandler
    @SendTo(value = "/topic/game/illegal")
    public IllegalMove handleException(RuntimeException exception) {
        return new IllegalMove(exception.getMessage(), "Illegal Move");
    }
}
