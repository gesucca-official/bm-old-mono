package com.gsc.bm.server.service.account;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.repo.external.UserCredentialsRecord;
import com.gsc.bm.server.repo.external.UserCredentialsRepository;
import com.gsc.bm.server.repo.external.UserDecksRecord;
import com.gsc.bm.server.repo.external.UserDecksRepository;
import com.gsc.bm.server.repo.internal.CardsGuiRepository;
import com.gsc.bm.server.service.account.model.UserAccountInfo;
import com.gsc.bm.server.service.account.model.UserGuiDeck;
import com.gsc.bm.server.service.factories.CardFactoryService;
import com.gsc.bm.server.service.factories.CharacterFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserCredentialsRepository userCredentialsRepo;
    private final UserDecksRepository userDecksRepo;
    private final CardsGuiRepository cardsGuiRepo;
    private final CardFactoryService cardFactoryService;
    private final CharacterFactoryService characterFactoryService;

    @Autowired
    public UserAccountServiceImpl(UserCredentialsRepository userCredentialsRepo,
                                  UserDecksRepository userDecksRepo,
                                  CardsGuiRepository cardsGuiRepo,
                                  CardFactoryService cardFactoryService,
                                  CharacterFactoryService characterFactoryService) {
        this.userCredentialsRepo = userCredentialsRepo;
        this.userDecksRepo = userDecksRepo;
        this.cardsGuiRepo = cardsGuiRepo;
        this.cardFactoryService = cardFactoryService;
        this.characterFactoryService = characterFactoryService;
    }

    @Override
    public UserAccountInfo loadUserAccountInfo(String username) {
        Optional<UserCredentialsRecord> rec = userCredentialsRepo.findById(username);
        return rec.map(userCredentialsRecord ->
                new UserAccountInfo(
                        userCredentialsRecord.getUsername(),
                        userCredentialsRecord.getEmail(),
                        userCredentialsRecord.getRole(),
                        getUserDecks(username),
                        getUserCardCollection()))
                .orElse(null);
    }

    @Override
    public void addUserDeck(String username, String deckId, UserGuiDeck deck) {
        userDecksRepo.save(
                new UserDecksRecord(username, deckId, fromGuiToStoredDeck(deck))
        );
    }

    // for now everyone has all the cards
    private Set<Card> getUserCardCollection() {
        return cardsGuiRepo.findAll()
                .stream()
                .map(cardsGuiRecord -> cardFactoryService.craftCard(cardsGuiRecord.getClazz()))
                .collect(Collectors.toSet());
    }

    private Set<UserGuiDeck> getUserDecks(String username) {
        return userDecksRepo.findAllByUsername(username)
                .stream()
                .map(d -> fromStoredToGuiDeck(d.getDeck(), d.getDeckId()))
                .collect(Collectors.toSet());
    }

    private UserGuiDeck fromStoredToGuiDeck(UserDecksRecord.UserStoredDeck deck, String deckId) {
        return new UserGuiDeck(
                deckId,
                characterFactoryService.craftCharacter(deck.getCharacterClazz()),
                cardFactoryService.craftCard(deck.getBasicActionCardClazz()),
                cardFactoryService.craftCard(deck.getLastResortCardClazz()),
                deck.getCharacterBoundCardsClazz().stream().map(cardFactoryService::craftCard).collect(Collectors.toList()),
                deck.getRegularCardsClazz().stream().map(cardFactoryService::craftCard).collect(Collectors.toList())
        );
    }

    private UserDecksRecord.UserStoredDeck fromGuiToStoredDeck(UserGuiDeck deck) {
        return new UserDecksRecord.UserStoredDeck(
                deck.getCharacter().getBindingName(),
                deck.getBasicActionCard().getBindingName(),
                deck.getLastResortCard().getBindingName(),
                deck.getCharacterBoundCards().stream().map(Card::getBindingName).collect(Collectors.toList()),
                deck.getRegularCards().stream().map(Card::getBindingName).collect(Collectors.toList())
        );
    }
}
