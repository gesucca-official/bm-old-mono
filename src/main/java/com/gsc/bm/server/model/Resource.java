package com.gsc.bm.server.model;

import java.io.Serializable;

public enum Resource implements Statistic, Serializable {
    HEALTH, ALERTNESS, VIOLENCE, TOXICITY, ALCOHOL
}
