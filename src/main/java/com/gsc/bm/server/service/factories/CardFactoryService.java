package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.cards.Card;

public interface CardFactoryService {

    String BASE_CARDS_PKG = "com.gsc.bm.server.model.cards.";

    Card craftCard(String cardClazz);

}
