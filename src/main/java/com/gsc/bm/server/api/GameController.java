package com.gsc.bm.server.api;

import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.IllegalMoveException;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.service.factories.GameFactoryService;
import com.gsc.bm.server.service.session.GameSessionService;
import com.gsc.bm.server.service.session.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class GameController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameFactoryService gameFactoryService;
    private final GameSessionService gameSessionService;
    private final QueueService queueService;

    @Autowired
    public GameController(SimpMessagingTemplate messagingTemplate,
                          GameFactoryService gameFactoryService,
                          GameSessionService gameSessionService,
                          QueueService queueService) {
        this.messagingTemplate = messagingTemplate;
        this.gameFactoryService = gameFactoryService;
        this.gameSessionService = gameSessionService;
        this.queueService = queueService;
    }

    @MessageMapping("/game/1vCom/join")
    @SendToUser("/queue/game/1vCom/ready")
    public String queueForQuick1vCom(String playerId) {
        return gameSessionService.newGame(
                gameFactoryService.craftQuick1vComGame(playerId)
        );
    }

    @MessageMapping("/game/4ffaCom/join")
    @SendToUser("/queue/game/4ffaCom/ready")
    public String queueForQuick4ffaCom(String playerId) {
        return gameSessionService.newGame(
                gameFactoryService.craftQuick4ffaComGame(playerId)
        );
    }

    @MessageMapping("/game/1v1/join")
    @SendTo("/topic/game/1v1/ready")
    public synchronized String queueForQuick1v1(String playerId) {
        Optional<List<String>> players = queueService.join1v1Queue(playerId);
        return players.map(playerIds -> gameSessionService.newGame(
                gameFactoryService.craftQuickMultiPlayerGame(playerIds)
        )).orElse(null);
    }

    @MessageMapping("/game/4ffa/join")
    @SendTo("/topic/game/4ffa/ready")
    public synchronized String queueForQuick4ffa(String playerId) {
        Optional<List<String>> players = queueService.join4ffaQueue(playerId);
        return players.map(playerIds -> gameSessionService.newGame(
                gameFactoryService.craftQuickMultiPlayerGame(playerIds)
        )).orElse(null);
    }

    @MessageMapping("/game/{gameId}/move")
    @SendTo("/topic/game/{gameId}/move")
    public String makeYourMove(@DestinationVariable String gameId, @Payload Move move) throws IllegalMoveException {
        gameSessionService.submitMoveToGame(move, () -> messagingTemplate.convertAndSend("/topic/game/" + gameId + "/update", move.getGameId()));
        return move.getPlayerId() + " submitted a Move";
    }

    @MessageMapping("game/{gameId}/{playerId}/view")
    @SendToUser("/queue/game/{gameId}/{playerId}/view")
    public Game getGameView(@DestinationVariable String gameId, @DestinationVariable String playerId) {
        return gameSessionService.getGame(gameId).getViewFor(playerId);
    }

    @MessageMapping("game/{gameId}/{playerId}/leave")
    public void leaveGame(@DestinationVariable String gameId, @DestinationVariable String playerId) {
        gameSessionService.leaveGame(gameId, playerId);
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/player/action/illegalMove")
    public String handleException(IllegalMoveException exception) {
        return exception.getWhatHeDid();
    }

}
