package com.gsc.bm.server.model.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class AbstractCard implements Card, LoadableCard, Serializable {

    private boolean isCharacterBound;
    private Map<Resource, Integer> cost;
    private int priority;
    private Set<CardTarget> canTarget;

    @Getter(value = AccessLevel.NONE)
    @JsonIgnore
    private String boundToCharacter;

    @Override
    public Optional<String> boundToCharacter() {
        if (isCharacterBound && boundToCharacter != null)
            return Optional.of(boundToCharacter);
        else return Optional.empty();
    }

    @Override
    public CardResolutionReport resolve(Game g, Move m) {
        applyOtherUnfathomableLogic(g, m);
        return new CardResolutionReport(
                applyEffectOnSelf(g.getSelf(m).getCharacter()),
                applyEffectOnTarget(g.getSelf(m).getCharacter(), g.getTarget(m).getCharacter())
        );
    }

    public abstract void applyOtherUnfathomableLogic(Game g, Move m);

    public abstract List<String> applyEffectOnSelf(Character self);

    public abstract List<String> applyEffectOnTarget(Character self, Character target);

    public AbstractCard() {
        isCharacterBound = false;
        priority = 1;
        cost = Map.of();
    }

    @Setter(AccessLevel.NONE)
    private String name;
    @Setter(AccessLevel.NONE)
    private String effect;

    @Override
    public void setGuiName(String name) {
        this.name = name;
    }

    @Override
    public void setGuiEffectDescription(String description) {
        this.effect = description;
    }
}