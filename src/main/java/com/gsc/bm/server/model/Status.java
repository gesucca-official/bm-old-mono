package com.gsc.bm.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public class Status implements Serializable {


    public enum StatusType implements Serializable {
        GOOD, BAD;
    }

    public interface StatusFunction extends Function<Supplier<Float>, Float>, Serializable {
    }

    private final String name;
    private final String description;

    private final StatusType type;
    private final Statistic impactedProperty;
    @JsonIgnore
    private final StatusFunction function;

    private Integer lastsForTurns; // boxing allows null value

    // lombok struggles with this constructor somehow
    public Status(String name, String description, StatusType type, Statistic impactedProperty, StatusFunction function, Integer lastsForTurns) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.impactedProperty = impactedProperty;
        this.function = function;
        this.lastsForTurns = lastsForTurns;
    }

    public void aTurnIsPassed() {
        if (lastsForTurns != null)
            this.lastsForTurns--;
    }
}
