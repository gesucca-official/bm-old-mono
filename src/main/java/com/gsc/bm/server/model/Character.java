package com.gsc.bm.server.model;

import lombok.Getter;

import java.util.EnumMap;
import java.util.Map;

@Getter
public abstract class Character {

    private final String name;
    protected final Map<Resource, Integer> resources = new EnumMap<>(Resource.class);

    public Character(String name, int hp, int speed) {
        this.name = name;
        this.resources.put(Resource.HEALTH, hp);
        this.resources.put(Resource.SPEED, speed);
    }

}
