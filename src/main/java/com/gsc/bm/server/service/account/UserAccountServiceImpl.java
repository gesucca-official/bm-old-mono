package com.gsc.bm.server.service.account;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.repo.external.UserCredentialsRecord;
import com.gsc.bm.server.repo.external.UserCredentialsRepository;
import com.gsc.bm.server.repo.internal.CardsGuiRepository;
import com.gsc.bm.server.service.account.model.UserAccountInfo;
import com.gsc.bm.server.service.factories.CardFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserCredentialsRepository userCredentialsRepo;
    private final CardsGuiRepository cardsGuiRepo;
    private final CardFactoryService cardFactoryService;

    @Autowired
    public UserAccountServiceImpl(UserCredentialsRepository userCredentialsRepo, CardsGuiRepository cardsGuiRepo, CardFactoryService cardFactoryService) {
        this.userCredentialsRepo = userCredentialsRepo;
        this.cardsGuiRepo = cardsGuiRepo;
        this.cardFactoryService = cardFactoryService;
    }

    @Override
    public UserAccountInfo loadUserAccountInfo(String username) {
        Optional<UserCredentialsRecord> rec = userCredentialsRepo.findById(username);
        if (rec.isPresent())
            return new UserAccountInfo(rec.get().getUsername(), rec.get().getEmail(), rec.get().getRole(),
                    getUserCardCollection());
        return null;
    }

    // for now everyone has all the cards
    private Set<Card> getUserCardCollection() {
        return cardsGuiRepo.findAll()
                .stream()
                .map(cardsGuiRecord -> cardFactoryService.craftCard(cardsGuiRecord.getClazz()))
                .collect(Collectors.toSet());
    }
}
