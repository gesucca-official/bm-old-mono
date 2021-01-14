package com.gsc.bm.server.service.account.model;

import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.service.view.model.deck.CharacterCardView;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserCollection {
    Set<CharacterCardView> characters;
    Set<Card> cards;
}
