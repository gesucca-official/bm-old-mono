package com.gsc.bm.server.service.view.model;

import com.gsc.bm.server.model.cards.Card;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ClientSelfPlayerView extends ClientPlayerView {
    private final String playerId;
    private final ClientCharacterView character;
    private final List<Card> cardsInHand;
    private final int deck;
}
