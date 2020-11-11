package com.gsc.bm.server.model.cards;

public interface LoadableCard extends Card {

    void setGuiName(String name);

    void setGuiEffectDescription(String description);

}
