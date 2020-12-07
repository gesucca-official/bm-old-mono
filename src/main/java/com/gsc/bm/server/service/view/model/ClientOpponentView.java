package com.gsc.bm.server.service.view.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClientOpponentView extends ClientPlayerView {
    private final String playerId;
    private final ClientCharacterView character;
    private final int cardsInHand;
    private final int deck;
}
