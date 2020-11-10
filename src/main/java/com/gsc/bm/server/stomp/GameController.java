package com.gsc.bm.server.stomp;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.IllegalMoveException;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.others.Games;
import com.gsc.bm.server.others.Queues;
import com.gsc.bm.server.service.GameFactoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
public class GameController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameFactoryService gameFactoryService;

    private final Games games = Games.getInstance();

    public GameController(SimpMessagingTemplate messagingTemplate, GameFactoryService gameFactoryService) {
        this.messagingTemplate = messagingTemplate;
        this.gameFactoryService = gameFactoryService;
    }

    @MessageMapping("/game/1vCom/join")
    @SendToUser("/queue/game/1vCom/ready")
    public String queueForQuick1vCom(String playerId) {
        Game game = gameFactoryService.craftQuick1vComGame(playerId);
        games.addNewGame(game);
        return game.getGameId();
    }

    @MessageMapping("/game/4ffaCom/join")
    @SendToUser("/queue/game/4ffaCom/ready")
    public String queueForQuick4ffaCom(String playerId) {
        Game game = gameFactoryService.craftQuick4ffaComGame(playerId);
        games.addNewGame(game);
        return game.getGameId();
    }

    @MessageMapping("/game/1v1/join")
    @SendTo("/topic/game/1v1/ready")
    public synchronized String queueForQuick1v1(String playerId) {
        log.info("Player " + playerId + " queued for a 1v1 Game");
        Optional<List<String>> players = Queues.getInstance().join1v1Queue(playerId);
        if (players.isPresent()) {
            Game game = gameFactoryService.craftQuickMultiPlayerGame(players.get());
            games.addNewGame(game);
            return game.getGameId();
        }
        return null;
    }

    @MessageMapping("/game/4ffa/join")
    @SendTo("/topic/game/4ffa/ready")
    public synchronized String queueForQuick4ffa(String playerId) {
        Optional<List<String>> players = Queues.getInstance().join4ffaQueue(playerId);
        if (players.isPresent()) {
            Game game = gameFactoryService.craftQuickMultiPlayerGame(players.get());
            games.addNewGame(game);
            return game.getGameId();
        }
        return null;
    }

    @MessageMapping("/game/{gameId}/move")
    @SendTo("/topic/game/{gameId}/move")
    public String makeYourMove(@DestinationVariable String gameId, @Payload Move move) throws IllegalMoveException {
        games.submitMove(move, () -> messagingTemplate.convertAndSend("/topic/game/" + gameId + "/update", move.getGameId()));
        return move.getPlayerId() + " submitted a Move";
    }

    @MessageMapping("game/{gameId}/{playerId}/view")
    @SendToUser("/queue/game/{gameId}/{playerId}/view")
    public Game getGameView(@DestinationVariable String gameId, @DestinationVariable String playerId) {
        Game view = games.getGame(gameId).getViewFor(playerId);
        if (games.getGame(gameId).isOver())
            games.destroyGame(gameId);
        return view;
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/player/action/illegalMove")
    public String handleException(IllegalMoveException exception) {
        return exception.getWhatHeDid();
    }

}
