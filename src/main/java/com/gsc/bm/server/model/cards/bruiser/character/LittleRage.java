package com.gsc.bm.server.model.cards.bruiser.character;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.Status;
import com.gsc.bm.server.model.cards.CharacterBoundCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;

import java.util.List;
import java.util.Set;

public class LittleRage extends CharacterBoundCard {

    public LittleRage() {
        super(BigBadBruiser.NAME);
        setCanTarget(Set.of(CardTarget.SELF));
        setPriority(2);
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        self.getStatuses().add(
                new Status(
                        "ONLY DISCOMFORT",
                        "Damage Taken: x0.75",
                        Status.StatusType.GOOD,
                        Status.StatusFlow.INPUT,
                        Damage.DamageType.HIT,
                        (incomingDamage -> incomingDamage * 0.75f),
                        0
                ));
        return List.of(
                "Gained status: ONLY DISCOMFORT",
                self.gainResource(Resource.VIOLENCE, 5)
        );
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        return null;
    }
}
