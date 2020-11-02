package com.gsc.bm.server.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class Character implements Serializable {

    private final String name;
    protected final Map<Resource, Integer> resources = new EnumMap<>(Resource.class);

    private final List<Status> statuses = new ArrayList<>();

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
        List<Status> expiredStatuses = new ArrayList<>();  // concurrent access problem with foreach
        for (Status status : statuses) {
            status.aTurnIsPassed();
            if (status.getLastsForTurns() < 0)
                expiredStatuses.add(status);
        }
        statuses.removeAll(expiredStatuses);
    }

    public Map.Entry<Resource, Integer> inflictDamage(Character target, int amount) {
        return target.loseResource(Resource.HEALTH, editDamage(amount));
    }

    public Map.Entry<Resource, Integer> inflictDamage(Character target, int amount, boolean applyGoodStatus, boolean applyBadStatus) {
        return target.loseResource(Resource.HEALTH, editDamage(amount), applyGoodStatus, applyBadStatus);
    }

    public Map.Entry<Resource, Integer> gainResource(Resource res, int amount) {
        return editResource(res, Math.abs(amount), true, true);
    }

    public Map.Entry<Resource, Integer> loseResource(Resource res, int amount) {
        return editResource(res, -Math.abs(amount), true, true);
    }

    public Map.Entry<Resource, Integer> loseResource(Resource res, int amount, boolean applyGoodStatus, boolean applyBadStatus) {
        return editResource(res, -Math.abs(amount), applyGoodStatus, applyBadStatus);
    }

    public boolean isDead() {
        return resources.get(Resource.HEALTH) <= 0;
    }

    // algebraic sum
    private Map.Entry<Resource, Integer> editResource(Resource res, int amount, boolean applyGoodStatus, boolean applyBadStatus) {
        int modifiedAmount = amount;
        if (applyBadStatus)
            modifiedAmount = applyStatusModifier(res, modifiedAmount, Status.StatusType.BAD);
        if (applyGoodStatus)
            modifiedAmount = applyStatusModifier(res, modifiedAmount, Status.StatusType.GOOD);

        resources.putIfAbsent(res, 0);
        getResources().put(res, resources.get(res) + modifiedAmount);
        return Map.entry(res, modifiedAmount);
    }

    private int editDamage(int amount) {
        int boostedOrDulledAmount = amount;
        for (Status s : statuses)
            if (s.getResourceAfflicted() == Resource.DAMAGE_MOD)
                boostedOrDulledAmount = s.getFunction().apply(amount);
        return boostedOrDulledAmount;
    }

    private int applyStatusModifier(Resource res, int amount, Status.StatusType goodOrBad) {
        for (Status status : statuses)
            if (status.getResourceAfflicted() == res && status.getType() == goodOrBad)
                return status.getFunction().apply(amount);
        return amount;
    }
}
