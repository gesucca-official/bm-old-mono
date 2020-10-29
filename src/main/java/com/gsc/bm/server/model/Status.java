package com.gsc.bm.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@AllArgsConstructor
@Getter
public class Status {

    public enum StatusType {
        GOOD, BAD
    }

    private final StatusType type;
    private final Resource resourceAfflicted;
    private int lastsForTurns;
    private final Function<Integer, Integer> function;

    public void aTurnIsPassed() {
        this.lastsForTurns--;
    }
}
