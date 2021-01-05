package com.gsc.bm.server.service.account.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserAccountInfo {

    String username;
    String email;
    String role;

}
