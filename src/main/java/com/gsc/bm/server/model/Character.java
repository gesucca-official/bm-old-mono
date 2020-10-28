package com.gsc.bm.server.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class Character {

    private final String name;
    protected final Map<Resource, Integer> resources = new EnumMap<>(Resource.class);

    private final List<StatusAliment> statuses = new ArrayList<>();

    public Character(String name, int hp, int speed) {
        this.name = name;
        this.resources.put(Resource.HEALTH, hp);
        this.resources.put(Resource.ALERTNESS, speed);
    }

    public void resolveTimeBasedEffects() {
        // resources based effect
        if (resources.get(Resource.TOXICITY) != null)
            loseResource(Resource.HEALTH, resources.get(Resource.TOXICITY));
        if (resources.get(Resource.ALCOHOL) != null) {
            loseResource(Resource.ALERTNESS, resources.get(Resource.ALCOHOL) / 2);
            loseResource(Resource.ALCOHOL, resources.get(Resource.ALCOHOL) / 2);
        }

        // clear statuses
        List<StatusAliment> expiredStatuses = new ArrayList<>();  // concurrent access problem with foreach
        for (StatusAliment status : statuses) {
            status.aTurnIsPassed();
            if (status.getLastsForTurns() < 0)
                expiredStatuses.add(status);
        }
        statuses.removeAll(expiredStatuses);
    }

    public int gainResource(Resource res, int amount) {
        int modifiedAmount = applyStatusModifier(res, amount);
        resources.putIfAbsent(res, 0);
        getResources().put(res, getResources().get(res) + modifiedAmount);
        return modifiedAmount;
    }

    public int loseResource(Resource res, int amount) {
        int modifiedAmount = applyStatusModifier(res, amount);
        resources.putIfAbsent(res, 0);
        getResources().put(res, getResources().get(res) - modifiedAmount);
        return modifiedAmount;
    }

    public boolean isDead() {
        return resources.get(Resource.HEALTH) <= 0;
    }

    private int applyStatusModifier(Resource res, int amount) {
        for (StatusAliment status : statuses)
            if (status.getResourceAfflicted() == res)
                return status.getFunction().apply(amount);
        return amount;
    }
}
