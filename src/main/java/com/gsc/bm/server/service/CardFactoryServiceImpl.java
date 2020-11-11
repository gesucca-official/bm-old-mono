package com.gsc.bm.server.service;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.cards.LoadableCard;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CardFactoryServiceImpl implements CardFactoryService {

    @Override
    public Card craftCard(Supplier<LoadableCard> cardConstructor) {
        LoadableCard card = cardConstructor.get();
        this.loadCard(card);
        return card;
    }

    @Override
    public void loadCard(LoadableCard card) {
        System.out.println(card.getClass().getName());
        card.setGuiName("name");
        card.setGuiEffectDescription("descr");
    }
}
