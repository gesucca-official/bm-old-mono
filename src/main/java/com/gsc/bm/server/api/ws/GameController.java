package com.gsc.bm.server.api.ws;

import com.gsc.bm.server.model.game.IllegalMoveException;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.service.factories.GameFactoryService;
import com.gsc.bm.server.service.session.GameSessionService;
import com.gsc.bm.server.service.session.QueueService;
import com.gsc.bm.server.service.session.model.QueuedPlayer;
import com.gsc.bm.server.service.view.ViewExtractorService;
import com.gsc.bm.server.service.view.model.client.ClientGameView;
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

    private final ViewExtractorService viewExtractorService;

    @Autowired
    public GameController(SimpMessagingTemplate messagingTemplate,
                          GameFactoryService gameFactoryService,
                          GameSessionService gameSessionService,
                          QueueService queueService,
                          ViewExtractorService viewExtractorService) {
        this.messagingTemplate = messagingTemplate;
        this.gameFactoryService = gameFactoryService;
        this.gameSessionService = gameSessionService;
        this.queueService = queueService;
        this.viewExtractorService = viewExtractorService;
    }

    @MessageMapping("/game/1vCom/join")
    @SendToUser("/queue/game/1vCom/ready")
    public String queueForQuick1vCom(String playerId) {
        return gameSessionService.newGame(
                gameFactoryService.craftQuick1vComGame(playerId));
    }

    @MessageMapping("/game/1v1/join")
    @SendTo("/topic/game/1v1/ready")
    public synchronized String queueForQuick1v1(String playerId) {
        return queueFor(QueueService.GameQueue.Q_1V1, playerId, true);
    }

    @MessageMapping("/game/ffa/join")
    @SendTo("/topic/game/ffa/joined")
    public synchronized List<QueuedPlayer> queueForQuickFfa(String playerId) {
        String game = queueFor(QueueService.GameQueue.Q_FFA, playerId, true);
        if (game != null)
            messagingTemplate.convertAndSend("/topic/game/ffa/ready", game);
        return queueService.getUsersInQueue(QueueService.GameQueue.Q_FFA);
    }

    @MessageMapping("/game/ffa/join/com")
    @SendTo("/topic/game/ffa/joined")
    public synchronized List<QueuedPlayer> addComPlayerToFfaQueue() {
        String game = queueFor(QueueService.GameQueue.Q_FFA, "QueuedComPlayer", false);
        if (game != null)
            messagingTemplate.convertAndSend("/topic/game/ffa/ready", game);
        return queueService.getUsersInQueue(QueueService.GameQueue.Q_FFA);
    }

    @MessageMapping("/game/ffa/start")
    @SendTo("/topic/game/ffa/ready")
    public synchronized String forceStartFfaGame() {
        Optional<List<QueuedPlayer>> queuedPlayers = queueService.flushQueue(QueueService.GameQueue.Q_FFA);
        if (queuedPlayers.isPresent()) {
            messagingTemplate.convertAndSend("/topic/game/ffa/joined", queueService.getUsersInQueue(QueueService.GameQueue.Q_FFA));
            return gameSessionService.newGame(
                    gameFactoryService.craftQuickMultiPlayerGame(queuedPlayers.get()));
        }
        return null;
    }

    @MessageMapping("/game/{gameId}/move")
    @SendTo("/topic/game/{gameId}/move")
    public String makeYourMove(@DestinationVariable String gameId, @Payload Move move) throws IllegalMoveException {
        gameSessionService.submitMoveToGame(move, () -> messagingTemplate.convertAndSend("/topic/game/" + gameId + "/update", move.getGameId()));
        return move.getPlayerId() + " submitted a Move";
    }

    @MessageMapping("/game/{gameId}/{playerId}/view")
    @SendToUser("/queue/game/{gameId}/{playerId}/view")
    public ClientGameView getGameView(@DestinationVariable String gameId, @DestinationVariable String playerId) {
        return viewExtractorService.extractViewFor(gameSessionService.getGame(gameId), playerId);
    }

    @MessageMapping("/game/{gameId}/{playerId}/leave")
    public void leaveGame(@DestinationVariable String gameId, @DestinationVariable String playerId) {
        gameSessionService.leaveGame(gameId, playerId);
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/player/action/illegalMove")
    public String handleException(IllegalMoveException exception) {
        return exception.getWhatHeDid();
    }

    private String queueFor(QueueService.GameQueue queue, String playerId, boolean human) {
        Optional<List<QueuedPlayer>> queuedPlayers = queueService.joinQueue(
                new QueuedPlayer(playerId, human), queue);
        return queuedPlayers.map(players -> gameSessionService.newGame(
                gameFactoryService.craftQuickMultiPlayerGame(players))).orElse(null);
    }

}
