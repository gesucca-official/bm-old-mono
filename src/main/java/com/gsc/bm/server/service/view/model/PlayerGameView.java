package com.gsc.bm.server.service.view.model;

import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.Player;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class PlayerGameView {
    private final String gameId;
    private final Map<String, Player> players;
    private final List<Move> resolvedMoves;
    private final Map<String, List<String>> timeBasedEffects;
    private final boolean over;
    private final String winner;
}