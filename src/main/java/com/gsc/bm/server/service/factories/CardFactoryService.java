package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.cards.LoadableCard;

import java.util.function.Supplier;

public interface CardFactoryService {

    String BASE_CARDS_PKG = "com.gsc.bm.server.model.cards.";

    Card craftCard(Supplier<LoadableCard> cardConstructor);

}
