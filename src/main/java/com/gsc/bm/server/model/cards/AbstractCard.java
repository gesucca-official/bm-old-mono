package com.gsc.bm.server.model.cards;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public abstract class AbstractCard implements Card, Serializable {

    private final String name;
    private final String effect;

    @Override
    public boolean isFixed() {
        return false;
    }
}
