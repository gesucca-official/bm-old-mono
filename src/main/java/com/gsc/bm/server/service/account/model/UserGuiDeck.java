package com.gsc.bm.server.service.account.model;

import com.gsc.bm.server.model.Character;
import com.gsc.bm.server.model.cards.Card;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserGuiDeck {
    String deckId;
    Character character;
    Card basicActionCard;
    Card lastResortCard;
    List<Card> characterBoundCards;
    List<Card> regularCards;
}
