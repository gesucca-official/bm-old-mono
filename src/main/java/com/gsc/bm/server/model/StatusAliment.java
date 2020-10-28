package com.gsc.bm.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@AllArgsConstructor
@Getter
public class StatusAliment {

    private final Function<Integer, Integer> function;
    private final Resource resourceAfflicted;
    private int lastsForTurns;

    public void aTurnIsPassed() {
        this.lastsForTurns--;
    }

}
