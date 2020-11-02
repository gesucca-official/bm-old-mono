package com.gsc.bm.server.stomp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameViewRequest {
    private final String playerId;
    private final String gameId;
}
