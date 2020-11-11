package com.gsc.bm.server.model.cards.bruiser.character;

import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.cards.CharacterBoundCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Set;

public class LittleSmack extends CharacterBoundCard {

    public LittleSmack() {
        super("Spazienzio de la Ucciso", "BOTTARELLA", "scarta una carta per giocare questa, ma non scarti questa. 5 danni");
    }

    @Override
    protected CardResolutionReport resolveCardLogic(Game g, Move m) {
        return new CardResolutionReport(
                null,
                List.of(g.getSelf(m).getCharacter().inflictDamage(
                        g.getTarget(m).getCharacter(),
                        new Damage(Damage.DamageType.HIT, 5)
                        ))
        );
    }

    @Override
    public Set<CardTarget> getCanTarget() {
        return Set.of(CardTarget.OPPONENT);
    }

}
