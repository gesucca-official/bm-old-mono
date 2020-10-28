package com.gsc.bm.server.model.cards;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class AbstractCard implements Card {

    private final String name;
    private final String effect;

}
