package com.gsc.bm.server.stomp;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.IllegalMoveException;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.Player;
import com.gsc.bm.server.service.PlayerFactoryService;
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

    private Game game;

    public GameController(PlayerFactoryService playerFactoryService) {
        this.playerFactoryService = playerFactoryService;
    }

    @MessageMapping("/game/join")
    @SendTo("/topic/game/update")
    public Game joinGame(String playerId) {
        playersQueue.add(playerFactoryService.craftRandomPlayer(playerId));
        if (playersQueue.size() >= 2) {
            game = new Game(playersQueue);
            playersQueue = new ArrayList<>();
            return game;
        } else return null;
    }

    @MessageMapping("/game/move")
    @SendTo("/topic/game/update")
    public Game makeYourMove(Move move) throws RuntimeException {
        game.submitMove(move);
        if (game.isReadyToResolveMoves()) {
            game.resolveMoves();
            return game;
        } else return null;
    }

    @MessageExceptionHandler
    @SendTo(value = "/topic/game/illegal")
    public IllegalMoveMessage handleException(IllegalMoveException exception) {
        return new IllegalMoveMessage(exception.getPlayerId(), exception.getWhatHeDid());
    }
}
