package com.gsc.bm.server.view;

import com.gsc.bm.server.model.game.Move;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class SlimGameView implements Serializable {
    private final String gameId;
    private final List<SlimPlayerView> players;

    private final List<Move> pendingMoves;

    private final List<Move> lastResolvedMoves;
    private final Map<String, List<String>> lastResolvedTimeBasedEffects;
}
