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
import java.util.*;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class AbstractCard implements Card, LoadableCard, Serializable {

    private boolean isItem;
    private boolean isBasicAction;
    private boolean isLastResort;
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
    public Map<CardTarget, List<String>> resolve(Game g, Move m) {
        applyOtherUnfathomableLogic(g, m);
        List<String> selfReport = applyEffectOnSelf(g.getSelf(m).getCharacter());
        List<String> oppoReport = applyEffectOnTarget(g.getSelf(m).getCharacter(), g.getTarget(m).getCharacter());
        return Map.of(
                CardTarget.SELF, selfReport == null ? List.of() : selfReport,
                CardTarget.OPPONENT, oppoReport == null ? List.of() : oppoReport
        );
    }

    public abstract void applyOtherUnfathomableLogic(Game g, Move m);

    public abstract List<String> applyEffectOnSelf(Character self);

    public abstract List<String> applyEffectOnTarget(Character self, Character target);

    public AbstractCard() {
        isLastResort = false;
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

    @Override
    public final boolean equals(Object o) {
        if (o instanceof AbstractCard) {
            AbstractCard e = (AbstractCard) o;
            return e.name.equals(this.name);
        } else
            return false;
    }

    protected <T> List<T> mergeList(List<T> list1, List<T> list2) {
        List<T> mergedList = new ArrayList<>(list1.size() + list2.size());
        mergedList.addAll(list1);
        mergedList.addAll(list2);
        return mergedList;
    }
}