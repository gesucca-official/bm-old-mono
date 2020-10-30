package com.gsc.bm.server.model;

public enum Resource {
    HEALTH, ALERTNESS, VIOLENCE, TOXICITY, ALCOHOL,
    DAMAGE_MOD // TODO damage boost cannot be treated as a resource in the long run, rework statuses
}
