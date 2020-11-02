package com.gsc.bm.server.model;

import java.io.Serializable;

public enum Resource implements Serializable {
    HEALTH, ALERTNESS, VIOLENCE, TOXICITY, ALCOHOL,
    DAMAGE_MOD // TODO damage boost cannot be treated as a resource in the long run, rework statuses
}
