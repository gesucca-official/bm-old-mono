package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.repo.internal.StarterDeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
                    IntStream.range(0, rec.getQty()).forEach(n -> copies.add(rec.getCardClazz()));
                    return copies;
                })
                .flatMap(Collection::stream)
                .map(cardFactoryService::craftCard)
                .collect(Collectors.toList());
    }

    // when all these special cards goes finally into db, all this things should not matter anymore
    // and I could finally stop replacing class strings

    @Override
    public Card craftBasicActionCard(Character character) {
        return cardFactoryService.craftCard(
                character.getBasicActionCard().getName().replace(CardFactoryService.BASE_CARDS_PKG, "")
        );
    }

    @Override
    public List<Card> craftCharacterBoundCards(Character character) {
        return character.getCharacterBoundCards()
                .stream()
                .map(Class::getName)
                .map(c -> c.replace(CardFactoryService.BASE_CARDS_PKG, ""))
                .map(cardFactoryService::craftCard)
                .collect(Collectors.toList());
    }

    @Override
    public Card craftLastResortCard(Character character) {
        return cardFactoryService.craftCard(
                character.getLastResortCard().getName().replace(CardFactoryService.BASE_CARDS_PKG, "")
        );
    }

}
