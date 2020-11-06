package com.gsc.bm.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Getter
public class Status implements Serializable {

    public enum StatusType implements Serializable {
        GOOD, BAD
    }

    public enum StatusFlow implements Serializable {
        INPUT, OUTPUT
    }

    public static final Set<StatusType> ALL = Set.of(StatusType.GOOD, StatusType.BAD);

    public interface StatusFunction extends Function<Float, Float>, Serializable {
    }

    private final String name;
    private final String description;

    private final StatusType type;
    private final StatusFlow flow;
    private final Statistic impactedProperty;
    @JsonIgnore
    private final StatusFunction function;

    private Integer lastsForTurns; // boxing allows null value

    // lombok struggles with this constructor somehow
    public Status(String name, String description, StatusType type, StatusFlow flow, Statistic impactedProperty, StatusFunction function, Integer lastsForTurns) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.flow = flow;
        this.impactedProperty = impactedProperty;
        this.function = function;
        this.lastsForTurns = lastsForTurns;
    }

    public void aTurnIsPassed() {
        if (lastsForTurns != null)
            this.lastsForTurns--;
    }

    public static Set<StatusType> invertViewPoint(Set<StatusType> s) {
        if (ALL.containsAll(s))
            return ALL;
        else {
            Set<StatusType> inverted = new HashSet<>(ALL);
            inverted.removeAll(s);
            return inverted;
        }
    }
}
