package com.gsc.bm.server.model.game;

public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(String playerThatMadeTheIllegalMoveId) {
        super(playerThatMadeTheIllegalMoveId);
    }
}
