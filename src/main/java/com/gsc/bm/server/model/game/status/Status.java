package com.gsc.bm.server.model.game.status;

import com.gsc.bm.server.model.Statistic;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class Status implements Serializable {

    public static final Set<StatusType> ALL = Set.of(StatusType.GOOD, StatusType.BAD);

    private final String name;
    private final String description;
    private final StatusType type;
    private final StatusFlow flow;
    private final Statistic impactedProperty;
    private final StatusAmountFunction amountFunction;
    @Builder.Default
    private final StatusAmountTypeFunction typeFunction = (type) -> type; // identity if not changed by builder

    private Integer lastsForTurns; // boxing allows null value

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
