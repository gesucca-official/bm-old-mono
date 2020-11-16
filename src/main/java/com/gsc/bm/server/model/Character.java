package com.gsc.bm.server.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    public abstract Set<String> getCharacterBoundCards();

    public List<String> resolveTimeBasedEffects() {
        List<String> effectsReport = new ArrayList<>();

        // resources based effect
        if (resources.get(Resource.TOXICITY) != null && resources.get(Resource.TOXICITY) > 0)
            effectsReport.add(
                    takeDamage(new Damage(Damage.DamageType.POISON, resources.get(Resource.TOXICITY))));

        if (resources.get(Resource.ALCOHOL) != null && resources.get(Resource.ALCOHOL) > 0) {
            effectsReport.add(
                    loseResource(Resource.ALERTNESS, resources.get(Resource.ALCOHOL) / 2));
            effectsReport.add(
                    loseResource(Resource.ALCOHOL, resources.get(Resource.ALCOHOL) / 2 + 1));
        }

        // clear statuses
        List<Status> expiredStatuses = new ArrayList<>();  // concurrent access problem with foreach
        for (Status status : statuses) {
            status.aTurnIsPassed();
            if (status.getLastsForTurns() < 0)
                expiredStatuses.add(status);
        }
        statuses.removeAll(expiredStatuses);
        effectsReport.addAll(expiredStatuses.stream().map(s -> "Cleared out of " + s.getName() + " status").collect(Collectors.toList()));

        return effectsReport;
    }

    // by default always apply statuses
    public String inflictDamage(Character target, Damage damage) {
        return inflictDamage(target, damage, Status.ALL);
    }

    public String inflictDamage(Character target, Damage damage, Set<Status.StatusType> statusToBeApplied) {
        return target.takeDamage(
                applyStatusToDamage(damage, statusToBeApplied, Status.StatusFlow.OUTPUT),
                Status.invertViewPoint(statusToBeApplied)
        );
    }

    public String takeDamage(Damage damage) {
        return takeDamage(damage, Status.ALL);
    }

    public String takeDamage(Damage damage, Set<Status.StatusType> statusToBeApplied) {
        return loseResource(
                Resource.HEALTH,
                applyStatusToDamage(damage, statusToBeApplied, Status.StatusFlow.INPUT).getAmount(),
                statusToBeApplied
        );
    }

    public String gainResource(Resource res, int amount) {
        return editResource(res, Math.abs(amount), Status.ALL);
    }

    public String loseResource(Resource res, int amount) {
        return editResource(res, -Math.abs(amount), Status.ALL);
    }

    public String loseResource(Resource res, int amount, Set<Status.StatusType> toBeApplied) {
        return editResource(res, -Math.abs(amount), toBeApplied);
    }

    public String emptyResource(Resource res) {
        resources.putIfAbsent(res, 0);
        return editResource(res, -getResources().get(res), Set.of());
    }

    public boolean isDead() {
        return resources.get(Resource.HEALTH) <= 0;
    }

    // algebraic sum
    private String editResource(Resource res, int amount, Set<Status.StatusType> toBeApplied) {
        resources.putIfAbsent(res, 0);
        int originalAmount = resources.get(res);
        getResources().put(res, resources.get(res) + applyStatusToResourceChange(res, amount, toBeApplied));
        return res + ": " + originalAmount + "->" + resources.get(res) + " (" + (resources.get(res) - originalAmount) + ")";
    }

    private Damage applyStatusToDamage(Damage damage, Set<Status.StatusType> toBeApplied, Status.StatusFlow flow) {
        for (Status s : statuses)
            if (s.getImpactedProperty() instanceof Damage.DamageType
                    && damage.getType() == s.getImpactedProperty()
                    && toBeApplied.contains(s.getType())
                    && flow == s.getFlow()
            ) {
                damage.setAmount(s.getFunction().apply((float) damage.getAmount()).intValue());
            }
        return damage;
    }

    private int applyStatusToResourceChange(Resource res, int amount, Set<Status.StatusType> toBeApplied) {
        for (Status status : statuses)
            if (status.getImpactedProperty() == res && toBeApplied.contains(status.getType()))
                return status.getFunction().apply((float) amount).intValue();
        return amount;
    }
}
