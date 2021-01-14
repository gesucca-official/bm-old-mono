package com.gsc.bm.server.service.account;

import com.gsc.bm.server.service.account.model.UserAccountInfo;
import com.gsc.bm.server.service.account.model.UserGuiDeck;

public interface UserAccountService {

    UserAccountInfo loadUserAccountInfo(String username);

    void addUserDeck(String username, UserGuiDeck deck);

}
