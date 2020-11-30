package com.gsc.bm.server.model.cards.bruiser;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.status.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BadEducation extends AbstractCard {

    public BadEducation() {
        super();
        setCanTarget(Set.of(CardTarget.OPPONENT));
        setCost(Map.of(Resource.VIOLENCE, 10));
    }

    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        return null;
    }

    @Override
    public List<String> applyEffectOnTarget(Character self, Character target) {
        List<Status> destroyedEquips = new ArrayList<>();  // concurrent access problem with foreach
        for (Status status : target.getStatuses()) {
            if (status.isEquip())
                destroyedEquips.add(status);
        }
        target.getStatuses().removeAll(destroyedEquips);
        return mergeList(
                destroyedEquips.stream().map(s -> "Removed " + s.getName() + " status!").collect(Collectors.toList()),
                List.of(
                        self.inflictDamage(target, new Damage(Damage.DamageType.HIT, 10))
                )
        );
    }
}
