package com.gsc.bm.server.service.account;

import com.gsc.bm.server.service.account.model.UserAccountInfo;

public interface UserAccountService {

    UserAccountInfo loadUserAccountInfo(String username);

}
