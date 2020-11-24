package com.gsc.bm.server.service.session;

import com.gsc.bm.server.model.game.*;
import com.gsc.bm.server.service.session.model.ActionLog;
import com.gsc.bm.server.service.session.model.UserSessionInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Log4j2
public class GameSessionServiceImpl implements GameSessionService {

    private static final Map<Game, Set<String>> _GAMES = new ConcurrentHashMap<>();

    private final ConnectionsService connectionsService;
    private final GameLoggingService gameLoggingService;

    @Autowired
    public GameSessionServiceImpl(ConnectionsService connectionsService, GameLoggingService gameLoggingService) {
        this.connectionsService = connectionsService;
        this.gameLoggingService = gameLoggingService;
    }

    @Override
    public synchronized String newGame(Game game) {
        // TODO this is an assumption: what happens if one player queued closes the game?
        Set<String> usersSubscribed = game.getPlayers().values()
                .stream()
                .filter(p -> !(p instanceof ComPlayer))
                .map(Player::getPlayerId)
                .peek(p -> connectionsService.changeUserActivity(p, UserSessionInfo.Activity.PLAYING))
                .collect(Collectors.toSet());

        _GAMES.put(game, usersSubscribed);
        log.info("Created Game " + game.getGameId() + " with users subscribed: " + usersSubscribed);
        gameLoggingService.log(game.getSlimGlobalView(), "STARTED", new ActionLog("Created Game", game.getSlimGlobalView()));

        return game.getGameId(); // return the id just for convenience
    }

    @Override
    public synchronized Game getGame(String gameId) {
        for (Game g : _GAMES.keySet())
            if (g.getGameId().equals(gameId))
                return g;
        throw new IllegalArgumentException(gameId);
    }

    @Override
    public synchronized void leaveGame(String gameId, String playerId) {
        _GAMES.get(getGame(gameId)).remove(playerId);
        connectionsService.changeUserActivity(playerId, UserSessionInfo.Activity.FREE);
        log.info("Player " + playerId + " left Game " + gameId);

        if (_GAMES.get(getGame(gameId)).isEmpty()) {
            gameLoggingService.flush(getGame(gameId).getSlimGlobalView());
            log.info("No one is anymore subscribed to Game " + gameId + " - removed!");
            _GAMES.remove(getGame(gameId));
        }
    }

    @Override
    public synchronized void submitMoveToGame(Move move, Runnable callback) throws IllegalMoveException {
        Game game = getGame(move.getGameId());
        game.submitMove(move);
        gameLoggingService.log(
                game.getSlimGlobalView(),
                "IN_PROGRESS",
                new ActionLog("Move submitted by " + move.getPlayerId(), move)
        );

        // is a player is an AI player, automatically submit its move
        for (Player p : game.getPlayers().values())
            if (p instanceof ComPlayer) {
                Move comMove = ((ComPlayer) p).chooseMove(game);
                game.submitMove(comMove);
                gameLoggingService.log(
                        game.getSlimGlobalView(),
                        "IN_PROGRESS",
                        new ActionLog("Move submitted by " + comMove.getPlayerId(), comMove)
                );
            }

        if (game.isReadyToResolveMoves()) {
            game.resolveMoves();
            gameLoggingService.log(
                    game.getSlimGlobalView(),
                    "IN_PROGRESS",
                    new ActionLog("Game updated after Moves Resolution", game.getSlimGlobalView())
            );
            callback.run();
        }
    }

}
