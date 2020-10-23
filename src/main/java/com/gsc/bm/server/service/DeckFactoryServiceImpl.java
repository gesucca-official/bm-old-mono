package com.gsc.bm.server.service;

import com.gsc.bm.server.model.Card;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeckFactoryServiceImpl implements DeckFactoryService {

    @Override
    public List<Card> craftCharacterStarterDeck(String characterName) {
        // TODO do it
        return new ArrayList<>();
    }
}
