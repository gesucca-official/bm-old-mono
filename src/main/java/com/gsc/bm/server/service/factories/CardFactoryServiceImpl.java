package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.cards.LoadableCard;
import com.gsc.bm.server.repo.CardKeywordsRepository;
import com.gsc.bm.server.repo.CardsGuiRecord;
import com.gsc.bm.server.repo.CardsGuiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CardFactoryServiceImpl implements CardFactoryService {

    private final CardsGuiRepository cardsGuiRepo;
    private final CardKeywordsRepository keywordsRepo;

    @Autowired
    public CardFactoryServiceImpl(CardsGuiRepository cardsGuiRepo, CardKeywordsRepository keywordsRepo) {
        this.cardsGuiRepo = cardsGuiRepo;
        this.keywordsRepo = keywordsRepo;
    }

    @Override
    public Card craftCard(Supplier<LoadableCard> cardConstructor) {
        LoadableCard card = cardConstructor.get();
        this.loadCard(card);
        return card;
    }

    private void loadCard(LoadableCard card) {
        String shortClassName = card.getClass().getName().replace(BASE_CARDS_PKG, "");
        CardsGuiRecord rec = cardsGuiRepo.findById(shortClassName)
                .orElseThrow(() -> new IllegalArgumentException("No such card in DB: " + shortClassName));
        card.setGuiName(rec.getGuiName());
        card.setGuiEffectDescription(buildHtml(card, rec.getGuiDescription()));
    }

    private String buildHtml(Card c, String d) {
        StringBuilder description = new StringBuilder();
        // TODO come on you are just lazy now
        if (c.isCharacterBound())
            description
                    .append("<p>")
                    .append(keywordsRepo.findById("fallbackMove").get().getHtml()) // TODO manage this kind of exceptions
                    .append("</p>");
        if (c.getPriority() > 1)
            description
                    .append("<p>")
                    .append(keywordsRepo.findById("firstStrike").get().getHtml())
                    .append("</p>");
        if (c.getPriority() < 1)
            description
                    .append("<p>")
                    .append(keywordsRepo.findById("lastStrike").get().getHtml())
                    .append("</p>");

        return description
                .append("<p>").append(d).append("</p>")
                .toString();
    }
}
