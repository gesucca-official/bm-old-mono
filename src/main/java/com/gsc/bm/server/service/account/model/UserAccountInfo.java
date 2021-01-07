package com.gsc.bm.server.service.account.model;

import com.gsc.bm.server.model.cards.Card;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserAccountInfo {

    String username;
    String email;
    String role;
    Set<Card> collection;

}
