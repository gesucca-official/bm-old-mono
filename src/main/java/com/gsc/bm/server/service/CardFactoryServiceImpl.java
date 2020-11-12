package com.gsc.bm.server.service;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.cards.LoadableCard;
import com.gsc.bm.server.repo.CardInfoRecord;
import com.gsc.bm.server.repo.CardInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CardFactoryServiceImpl implements CardFactoryService {

    private final CardInfoRepository cardInfoRepo;

    @Autowired
    public CardFactoryServiceImpl(CardInfoRepository cardInfoRepo) {
        this.cardInfoRepo = cardInfoRepo;
    }

    @Override
    public Card craftCard(Supplier<LoadableCard> cardConstructor) {
        LoadableCard card = cardConstructor.get();
        this.loadCard(card);
        return card;
    }

    private void loadCard(LoadableCard card) {
        String shortClassName = card.getClass().getName().replace(BASE_CARDS_PKG, "");
        CardInfoRecord rec = cardInfoRepo.findById(shortClassName)
                .orElseThrow(() -> new IllegalArgumentException("No such card in DB: " + shortClassName));
        card.setGuiName(rec.getGuiName());
        card.setGuiEffectDescription(rec.getGuiDescription());
    }
}
