package com.gsc.bm.server.model.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Move {

    private final String playedCardName;
    private final String playerId;
    private final String targetId;
    private final String gameId;

}
