package com.gsc.bm.server.model;

import java.util.EnumMap;
import java.util.Map;

public abstract class Character {

    private final String name;
    protected final Map<Resource, Integer> resources = new EnumMap<>(Resource.class);

    public Character(String name, int hp, int ap) {
        this.name = name;
        this.resources.put(Resource.HP, hp);
        this.resources.put(Resource.AP, ap);
    }

    public String getName() {
        return name;
    }

    public Map<Resource, Integer> getResources() {
        // attempting to clone to not let this be modified
        return new EnumMap<>(resources);
    }

    public void gainResource(Resource res, int amount) {
        resources.put(res, resources.get(res) + amount);
    }

    // TODO manage when resource is not already present?
    public void loseResource(Resource res, int amount) {
        resources.put(res, resources.get(res) - amount);
    }

    public boolean isDead() {
        return resources.get(Resource.HP) <= 0;
    }

}
