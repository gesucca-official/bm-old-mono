package com.gsc.bm.server.stomp;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.IllegalMoveException;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.others.Games;
import com.gsc.bm.server.others.Queues;
import com.gsc.bm.server.service.GameFactoryService;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class GameController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameFactoryService gameFactoryService;

    public GameController(SimpMessagingTemplate messagingTemplate, GameFactoryService gameFactoryService) {
        this.messagingTemplate = messagingTemplate;
        this.gameFactoryService = gameFactoryService;
    }

    @MessageMapping("/game/1vCom/join")
    public void queueForQuick1vCom(String playerId) {
        Game game = gameFactoryService.craftQuick1vComGame(playerId);
        Games.getInstance().addNewGame(game);
        messagingTemplate.convertAndSend("/topic/game/update", game.getGameId());
    }

    @MessageMapping("/game/4ffaCom/join")
    public void queueForQuick4ffaCom(String playerId) {
        Game game = gameFactoryService.craftQuick4ffaComGame(playerId);
        Games.getInstance().addNewGame(game);
        messagingTemplate.convertAndSend("/topic/game/update", game.getGameId());
    }

    @MessageMapping("/game/1v1/join")
    @SendTo("/topic/game/1v1/join")
    public String queueForQuick1v1(String playerId) {
        Optional<List<String>> players = Queues.getInstance().join1v1Queue(playerId);
        if (players.isPresent()) {
            Game game = gameFactoryService.craftQuickMultiPlayerGame(players.get());
            Games.getInstance().addNewGame(game);
            messagingTemplate.convertAndSend("/topic/game/update", game.getGameId());
        }
        return playerId + " queued for 1v1 game";
    }

    @MessageMapping("/game/4ffa/join")
    @SendTo("/topic/game/4ffa/join")
    public String queueForQuick4ffa(String playerId) {
        Optional<List<String>> players = Queues.getInstance().join4ffaQueue(playerId);
        if (players.isPresent()) {
            Game game = gameFactoryService.craftQuickMultiPlayerGame(players.get());
            Games.getInstance().addNewGame(game);
            messagingTemplate.convertAndSend("/topic/game/update", game.getGameId());
        }
        return playerId + " queued for 4ffa game";
    }

    @MessageMapping("/game/move")
    @SendTo("/topic/game/move")
    public String makeYourMove(Move move) throws IllegalMoveException {
        Games.getInstance().submitMove(move, () -> messagingTemplate.convertAndSend("/topic/game/update", move.getGameId()));
        return move.getPlayerId() + " submitted a Move";
    }

    @MessageMapping("game/view")
    @SendToUser("queue/game/view")
    public Game getGameView(GameViewRequest gameViewRequest) {
        return Games.getInstance().getGame(gameViewRequest.getGameId()).getViewFor(gameViewRequest.getPlayerId());
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/game/move")
    public String handleException(IllegalMoveException exception) {
        return exception.getWhatHeDid();
    }

}
