package com.gsc.bm.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.function.Function;

@AllArgsConstructor
@Getter
public class Status implements Serializable {

    public enum StatusType implements Serializable {
        GOOD, BAD
    }

    public interface StatusFunction extends Function<Integer, Integer>, Serializable {
        // TODO here we can add a toString
    }

    private final StatusType type;
    private final Resource resourceAfflicted;
    private int lastsForTurns;
    private final StatusFunction function;

    public void aTurnIsPassed() {
        this.lastsForTurns--;
    }
}
