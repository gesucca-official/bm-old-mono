package com.gsc.bm.server.service;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.cards.LoadableCard;
import com.gsc.bm.server.repo.StarterDeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DeckFactoryServiceImpl implements DeckFactoryService {

    private final StarterDeckRepository starterDeckRepo;
    private final CardFactoryService cardFactoryService;

    @Autowired
    public DeckFactoryServiceImpl(StarterDeckRepository starterDeckRepo, CardFactoryService cardFactoryService) {
        this.starterDeckRepo = starterDeckRepo;
        this.cardFactoryService = cardFactoryService;
    }

    @Override
    public List<Card> craftCharacterStarterDeck(String pgClazz) {
        return starterDeckRepo.findAllByPgClazz(pgClazz.replace(CardFactoryService.BASE_CARDS_PKG, ""))
                .stream()
                .map(rec -> {
                    List<String> copies = new ArrayList<>(rec.getQty());
                    IntStream.range(0, rec.getQty()).forEach(
                            n -> copies.add(CardFactoryService.BASE_CARDS_PKG + rec.getCardClazz())
                    );
                    return copies;
                })
                .flatMap(Collection::stream)
                .map(cardClazz -> {
                    Supplier<LoadableCard> supplier = () -> {
                        try {
                            return (LoadableCard) Class.forName(cardClazz).getConstructor().newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    };
                    return cardFactoryService.craftCard(supplier);
                }).collect(Collectors.toList());
    }
}
