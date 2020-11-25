package com.gsc.bm.server.service.view;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.Player;
import com.gsc.bm.server.service.view.model.PlayerGameView;
import com.gsc.bm.server.service.view.model.SlimGameView;
import com.gsc.bm.server.service.view.model.SlimPlayerView;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewExtractorServiceImpl implements ViewExtractorService {

    @Override
    public SlimGameView extractGlobalSlimView(Game game) {
        byte[] bytes = SerializationUtils.serialize(game);
        Game gameClone = (Game) SerializationUtils.deserialize(bytes);

        assert gameClone != null;
        List<SlimPlayerView> slimPlayers = gameClone.getPlayers().values()
                .stream()
                .map(p -> SlimPlayerView.builder()
                        .playerId(p.getPlayerId())
                        .character(p.getCharacter().getSlimView())
                        .cardsInHand(p.getCardsInHand()
                                .stream()
                                .map(Card::getName)
                                .collect(Collectors.toList())
                        ).deck(p.getDeck()
                                .stream()
                                .map(Card::getName)
                                .collect(Collectors.toList())
                        ).build())
                .collect(Collectors.toList());

        return SlimGameView.builder()
                .gameId(gameClone.getGameId())
                .players(slimPlayers)
                .resolvedMoves(gameClone.getResolvedMoves()
                        .stream()
                        .map(Move::getSlimView)
                        .collect(Collectors.toList())
                ).timeBasedEffects(gameClone.getTimeBasedEffects())
                .over(game.isOver())
                .winner(game.getWinner().orElse("NONE"))
                .build();
    }

    @Override
    public PlayerGameView extractViewFor(Game game, String playerId) {
        byte[] bytes = SerializationUtils.serialize(game);
        Game gameClone = (Game) SerializationUtils.deserialize(bytes);

        assert gameClone != null;
        PlayerGameView gameViewForPlayer = PlayerGameView.builder()
                .gameId(gameClone.getGameId())
                .players(gameClone.getPlayers())
                .resolvedMoves(gameClone.getResolvedMoves())
                .timeBasedEffects(gameClone.getTimeBasedEffects())
                .over(gameClone.isOver())
                .winner(gameClone.getWinner().orElse("NONE"))
                .build();

        for (Player oppo : gameViewForPlayer.getPlayers().values())
            if (!oppo.getPlayerId().equals(playerId)) {
                List<Card> hiddenCards = oppo.getCardsInHand()
                        .stream()
                        .map(c -> Card.UNKNOWN_CARD)
                        .collect(Collectors.toList());
                oppo.getCardsInHand().clear();
                oppo.getCardsInHand().addAll(hiddenCards);
            }
        for (Player p : gameViewForPlayer.getPlayers().values()) {
            List<Card> hiddenCards = p.getDeck()
                    .stream()
                    .map(c -> Card.UNKNOWN_CARD)
                    .collect(Collectors.toList());
            p.getDeck().clear();
            p.getDeck().addAll(hiddenCards);
        }
        return gameViewForPlayer;
    }
}
