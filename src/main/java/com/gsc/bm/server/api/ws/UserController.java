package com.gsc.bm.server.api.ws;

import com.gsc.bm.server.service.account.UserAccountService;
import com.gsc.bm.server.service.account.model.UserAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserAccountService service;

    @Autowired
    public UserController(UserAccountService service) {
        this.service = service;
    }

    @MessageMapping("/user/{username}/account")
    @SendToUser("/queue/user/{username}/account")
    public UserAccountInfo getGameView(@DestinationVariable String username) {
        return service.loadUserAccountInfo(username);
    }
}
