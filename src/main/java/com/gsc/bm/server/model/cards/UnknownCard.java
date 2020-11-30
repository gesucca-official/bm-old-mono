package com.gsc.bm.server.model.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class UnknownCard implements Card {

    UnknownCard() {
    }

    @Override
    public String getName() {
        return "Unknown Card";
    }

    @Override
    @JsonIgnore
    public String getEffect() {
        return null;
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public boolean isBasicAction() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isLastResort() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCharacterBound() {
        return false;
    }

    @Override
    @JsonIgnore
    public Optional<String> boundToCharacter() {
        return Optional.empty();
    }

    @Override
    @JsonIgnore
    public int getPriority() {
        return 0;
    }

    @Override
    @JsonIgnore
    public Set<CardTarget> getCanTarget() {
        return null;
    }

    @Override
    @JsonIgnore
    public Map<Resource, Integer> getCost() {
        return null;
    }

    @Override
    @JsonIgnore
    public Map<CardTarget, List<String>> resolve(Game game, Move move) {
        return null;
    }
}
