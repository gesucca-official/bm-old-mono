package com.gsc.bm.server.service;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.cards.LoadableCard;

import java.util.function.Supplier;

public interface CardFactoryService {

    Card craftCard(Supplier<LoadableCard> cardConstructor);

    void loadCard(LoadableCard card);
}
