package com.gsc.bm.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Move {

    private final Card cardPlayed;
    private final String targetPlayerId;

}
