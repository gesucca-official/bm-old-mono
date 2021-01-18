package com.gsc.bm.server.api.ws;

import com.gsc.bm.server.service.factories.GameFactoryService;
import com.gsc.bm.server.service.session.GameSessionService;
import com.gsc.bm.server.service.session.QueueService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class OpenGameQueuesController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameFactoryService gameFactoryService;
    private final GameSessionService gameSessionService;
    private final QueueService queueService;

    public OpenGameQueuesController(SimpMessagingTemplate messagingTemplate,
                                    GameFactoryService gameFactoryService,
                                    GameSessionService gameSessionService,
                                    QueueService queueService) {
        this.messagingTemplate = messagingTemplate;
        this.gameFactoryService = gameFactoryService;
        this.gameSessionService = gameSessionService;
        this.queueService = queueService;
    }

    @MessageMapping("/game/open/1vCom/join/{username}/{deckId}")
    @SendToUser("/queue/game/open/1vCom/ready")
    public String queueForOpen1vCom(@DestinationVariable String username, @DestinationVariable String deckId) {
        return gameSessionService.newGame(
                gameFactoryService.craftOpen1vComGame(username, deckId));
    }

    /*@MessageMapping("/game/open/1v1/join")
    @SendTo("/topic/game/open/1v1/ready")
    public synchronized String queueForOpen1v1(String playerId) {
        return queueFor(QueueService.GameQueue.Q_1V1, playerId, true);
    }

    @MessageMapping("/game/open/ffa/join")
    @SendTo("/topic/game/open/ffa/joined")
    public synchronized List<QueuedPlayer> queueForOpenFfa(String playerId) {
        String game = queueFor(QueueService.GameQueue.Q_FFA, playerId, true);
        if (game != null)
            messagingTemplate.convertAndSend("/topic/game/ffa/ready", game);
        return queueService.getUsersInQueue(QueueService.GameQueue.Q_FFA);
    }

    @MessageMapping("/game/open/ffa/join/com")
    @SendTo("/topic/game/open/ffa/joined")
    public synchronized List<QueuedPlayer> addComPlayerToOpenFfaQueue() {
        String game = queueFor(QueueService.GameQueue.Q_FFA, "QueuedComPlayer", false);
        if (game != null)
            messagingTemplate.convertAndSend("/topic/game/ffa/ready", game);
        return queueService.getUsersInQueue(QueueService.GameQueue.Q_FFA);
    }

    @MessageMapping("/game/open/ffa/start")
    @SendTo("/topic/game/open/ffa/ready")
    public synchronized String forceStartFfaGame() {
        Optional<List<QueuedPlayer>> queuedPlayers = queueService.flushQueue(QueueService.GameQueue.Q_FFA);
        if (queuedPlayers.isPresent()) {
            messagingTemplate.convertAndSend("/topic/game/ffa/joined", queueService.getUsersInQueue(QueueService.GameQueue.Q_FFA));
            return gameSessionService.newGame(
                    gameFactoryService.craftopenMultiPlayerGame(queuedPlayers.get()));
        }
        return null;
    }

    private String queueFor(QueueService.GameQueue queue, String playerId, boolean human) {
        Optional<List<QueuedPlayer>> queuedPlayers = queueService.joinQueue(
                new QueuedPlayer(playerId, human), queue);
        return queuedPlayers.map(players -> gameSessionService.newGame(
                gameFactoryService.craftopenMultiPlayerGame(players))).orElse(null);
    }*/
}
