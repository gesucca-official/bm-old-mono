package com.gsc.bm.server.stomp;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.IllegalMoveException;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.Player;
import com.gsc.bm.server.service.PlayerFactoryService;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    private final SimpMessagingTemplate messagingTemplate;

    private final PlayerFactoryService playerFactoryService;
    private final List<Player> queue1v1 = new ArrayList<>(); // TODO for now a single queue will do

    private Game game;

    public GameController(PlayerFactoryService playerFactoryService, SimpMessagingTemplate messagingTemplate) {
        this.playerFactoryService = playerFactoryService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/game/1v1/join")
    @SendTo("/topic/game/1v1/join")
    public String queueFor1v1(String playerId) {
        queue1v1.add(playerFactoryService.craftRandomPlayer(playerId));
        if (queue1v1.size() >= 2) {
            game = new Game(queue1v1);
            queue1v1.clear();
            messagingTemplate.convertAndSend("/topic/game/update", game.getGameId());
        }
        return playerId + " queued for 1v1 game. Players in queue: " + queue1v1.size();
    }

    @MessageMapping("/game/move")
    @SendTo("/topic/game/move")
    public String makeYourMove(Move move) throws RuntimeException {
        game.submitMove(move);
        if (game.isReadyToResolveMoves()) {
            game.resolveMoves();
            messagingTemplate.convertAndSend("/topic/game/update", game.getGameId());
        }
        return move.getPlayerId() + " submitted a Move. Moves submitted: " + game.getPendingMoves().size();
    }

    @MessageMapping("game/view")
    @SendToUser("queue/game/view")
    public Game getGameView(GameViewRequest gameViewRequest) {
        return game.getViewFor(gameViewRequest.getPlayerId());
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/game/move")
    public String handleException(IllegalMoveException exception) {
        return exception.getWhatHeDid();
    }

}
