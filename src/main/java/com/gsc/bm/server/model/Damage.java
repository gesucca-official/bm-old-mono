package com.gsc.bm.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Damage implements Serializable {

    public enum DamageType {
        HIT, CUT, POISON
    }

    private final DamageType type;
    private int amount;

}
