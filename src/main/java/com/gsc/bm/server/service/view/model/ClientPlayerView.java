package com.gsc.bm.server.service.view.model;

import com.gsc.bm.server.model.cards.Card;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Stack;

@Builder
@Getter
public class ClientPlayerView {
    private final String playerId;
    private final ClientCharacterView character;
    private final List<Card> cardsInHand;
    private final Stack<Card> deck;
}
