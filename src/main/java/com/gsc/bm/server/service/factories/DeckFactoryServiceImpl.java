package com.gsc.bm.server.service.factories;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.repo.internal.StarterDeckBasicCardsRecord;
import com.gsc.bm.server.repo.internal.StarterDeckBasicCardsRepository;
import com.gsc.bm.server.repo.internal.StarterDeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DeckFactoryServiceImpl implements DeckFactoryService {

    private final StarterDeckRepository starterDeckRepo;
    private final StarterDeckBasicCardsRepository starterDeckBasicCardsRepo;
    private final CardFactoryService cardFactoryService;

    @Autowired
    public DeckFactoryServiceImpl(StarterDeckRepository starterDeckRepo,
                                  StarterDeckBasicCardsRepository starterDeckBasicCardsRepo,
                                  CardFactoryService cardFactoryService) {
        this.starterDeckRepo = starterDeckRepo;
        this.starterDeckBasicCardsRepo = starterDeckBasicCardsRepo;
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

    // some code can be factored out of these three but I'm lazy

    @Override
    public Card craftBasicActionCard(Character character) {
        Optional<StarterDeckBasicCardsRecord> basics = starterDeckBasicCardsRepo.findById(
                character.getClass().getName().replace(CardFactoryService.BASE_CARDS_PKG, ""));
        if (basics.isPresent())
            return cardFactoryService.craftCard(basics.get().getBasicClazz());
        else
            throw new ValueNotFoundException(character.getClass().getName().replace(CardFactoryService.BASE_CARDS_PKG, ""));
    }

    @Override
    public List<Card> craftCharacterBoundCards(Character character) {
        Optional<StarterDeckBasicCardsRecord> basics = starterDeckBasicCardsRepo.findById(
                character.getClass().getName().replace(CardFactoryService.BASE_CARDS_PKG, ""));
        if (basics.isPresent())
            return List.of(
                    cardFactoryService.craftCard(basics.get().getChBoundClazz1()),
                    cardFactoryService.craftCard(basics.get().getChBoundClazz2())
            );
        else
            throw new ValueNotFoundException(character.getClass().getName().replace(CardFactoryService.BASE_CARDS_PKG, ""));

    }

    @Override
    public Card craftLastResortCard(Character character) {
        Optional<StarterDeckBasicCardsRecord> basics = starterDeckBasicCardsRepo.findById(
                character.getClass().getName().replace(CardFactoryService.BASE_CARDS_PKG, ""));
        if (basics.isPresent())
            return cardFactoryService.craftCard(basics.get().getLastResortClazz());
        else
            throw new ValueNotFoundException(character.getClass().getName().replace(CardFactoryService.BASE_CARDS_PKG, ""));

    }

}
