package com.gsc.bm.server.model.cards.com.items;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.Damage;
import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.AbstractItemCard;
import com.gsc.bm.server.model.game.Game;
import com.gsc.bm.server.model.game.Move;
import com.gsc.bm.server.model.game.Timer;

import java.util.List;

public class MarvelousCocktail extends AbstractItemCard {


    @Override
    public void applyOtherUnfathomableLogic(Game g, Move m) {
    }

    @Override
    public List<String> applyEffectOnSelf(Character self) {
        self.getImmunities().add(Resource.TOXICITY);
        self.getImmunities().add(Resource.ALCOHOL);
        self.getTimers().add(
                new Timer(
                        "Marvelous Cocktail",
                        c -> {
                            c.getImmunities().clear();
                            c.getStatuses().clear();
                            c.takeDamage(new Damage(Damage.DamageType.POISON, 20));
                        }, 5)
        );
        return List.of(
                "Gained a temporary Chemical Immunity",
                "Metabolism definitively corrupted!!",
                self.gainResource(Resource.ALCOHOL, 20),
                self.gainResource(Resource.TOXICITY, 20)
        );
    }
}
