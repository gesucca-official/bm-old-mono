package com.gsc.bm.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore // these could be different for each character
    protected final Map<Attribute, Float> attributes = Map.of(
            Attribute.STRENGTH, 1f,
            Attribute.DEXTERITY, 1f,
            Attribute.METABOLISM, 1f,
            Attribute.STURDINESS, 1f
    );

    private final List<Status> statuses = new ArrayList<>();

    public Character(String name, int hp, int speed) {
        this.name = name;
        this.resources.put(Resource.HEALTH, hp);
        this.resources.put(Resource.ALERTNESS, speed);

    }

    public void resolveTimeBasedEffects() {
        // resources based effect
        if (resources.get(Resource.TOXICITY) != null)
            takeDamage(new Damage(Damage.DamageType.POISON, resources.get(Resource.TOXICITY)));
        if (resources.get(Resource.ALCOHOL) != null) {
            loseResource(Resource.ALERTNESS, resources.get(Resource.ALCOHOL) / 2);
            loseResource(Resource.ALCOHOL, resources.get(Resource.ALCOHOL) / 2 + 1);
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

    // by default always apply statuses
    public String inflictDamage(Character target, Damage damage) {
        Damage outgoingDamage = applyStatusToDamageOutput(damage);
        return target.takeDamage(
                outgoingDamage
        );
    }

    public String inflictDamage(Character target, Damage damage, boolean applyGoodStatus, boolean applyBadStatus) {
        Damage outgoingDamage = applyStatusToDamageOutput(damage);
        return target.takeDamage(
                outgoingDamage,
                applyBadStatus, // good status for me is bad status for you!
                applyGoodStatus
        );
    }

    public String takeDamage(Damage damage) {
        return loseResource(
                Resource.HEALTH,
                applyStatusToDamageInput(damage).getAmount(),
                true,
                true
        );
    }

    public String takeDamage(Damage damage, boolean applyGoodStatus, boolean applyBadStatus) {
        Damage damageActuallyTaken = applyStatusToDamageInput(damage);
        return loseResource(
                Resource.HEALTH,
                damageActuallyTaken.getAmount(),
                applyGoodStatus,
                applyBadStatus
        );
    }

    public String gainResource(Resource res, int amount) {
        return editResource(res, Math.abs(amount), true, true);
    }

    public String loseResource(Resource res, int amount) {
        return editResource(res, -Math.abs(amount), true, true);
    }

    public String loseResource(Resource res, int amount, boolean applyGoodStatus, boolean applyBadStatus) {
        return editResource(res, -Math.abs(amount), applyGoodStatus, applyBadStatus);
    }

    public boolean isDead() {
        return resources.get(Resource.HEALTH) <= 0;
    }

    // algebraic sum
    private String editResource(Resource res, int amount, boolean applyGoodStatus, boolean applyBadStatus) {
        resources.putIfAbsent(res, 0);
        int originalAmount = resources.get(res);

        int modifiedAmount = amount;
        if (applyBadStatus)
            modifiedAmount = applyStatusToResourceChange(res, modifiedAmount, Status.StatusType.BAD);
        if (applyGoodStatus)
            modifiedAmount = applyStatusToResourceChange(res, modifiedAmount, Status.StatusType.GOOD);

        getResources().put(res, resources.get(res) + modifiedAmount);
        return res + ": " + originalAmount + "->" + resources.get(res);
    }

    // strength and dexterity impact hit and cut damage output
    private Damage applyStatusToDamageOutput(Damage damage) {
        // TODO this can be refactored more cleverly
        for (Status s : statuses)
            if (s.getImpactedProperty() instanceof Attribute) {
                switch ((Attribute) s.getImpactedProperty()) {
                    case STRENGTH:
                        damage.setAmount(
                                damage.getType() == Damage.DamageType.HIT ?
                                        (int) (s.getFunction().apply(() -> attributes.get(Attribute.STRENGTH)) * damage.getAmount())
                                        : damage.getAmount());
                    case DEXTERITY:
                        damage.setAmount(
                                damage.getType() == Damage.DamageType.CUT ?
                                        (int) (s.getFunction().apply(() -> attributes.get(Attribute.DEXTERITY)) * damage.getAmount())
                                        : damage.getAmount());
                }
            }
        return damage;
    }

    // metabolism impact poison damage input
    private Damage applyStatusToDamageInput(Damage damage) {
        // TODO also this can be refactored more cleverly
        for (Status s : statuses)
            if (s.getImpactedProperty() instanceof Attribute) {
                if (s.getImpactedProperty() == Attribute.METABOLISM)
                    damage.setAmount(
                            damage.getType() == Damage.DamageType.POISON ?
                                    (int) (s.getFunction().apply(() -> attributes.get(Attribute.METABOLISM)) * damage.getAmount())
                                    : damage.getAmount());
                if (s.getImpactedProperty() == Attribute.STURDINESS)
                    damage.setAmount(
                            damage.getType() == Damage.DamageType.HIT ?
                                    (int) (s.getFunction().apply(() -> attributes.get(Attribute.STURDINESS)) * damage.getAmount())
                                    : damage.getAmount());
            }
        return damage;
    }

    private int applyStatusToResourceChange(Resource res, int amount, Status.StatusType goodOrBad) {
        for (Status status : statuses)
            if (status.getImpactedProperty() == res && status.getType() == goodOrBad)
                // TODO code smell, CODE SMELL
                return Integer.parseInt(String.valueOf(status.getFunction().apply(() -> (float) amount)));
        return amount;
    }
}
