package com.gsc.bm.server.model.game.status;

import com.gsc.bm.server.model.Statistic;

import java.io.Serializable;
import java.util.function.Function;

public interface StatusAmountTypeFunction extends Function<Statistic, Statistic>, Serializable {
    // this is just so it can extends serializable
}
