package com.gsc.bm.server.stomp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class IllegalMoveMessage {
    String playerId;
    String message;
}
