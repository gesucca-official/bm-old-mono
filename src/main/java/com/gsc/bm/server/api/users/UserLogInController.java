package com.gsc.bm.server.api.users;

import com.gsc.bm.server.service.session.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/sign-in")
public class UserLogInController {

    private final AuthService authService;

    @Autowired
    public UserLogInController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/validate")
    public boolean validateCredentials(@RequestBody Map<String, String> credentials) {
        try {
            authService.getAuthTokenOrFail(credentials.get("username"), credentials.get("password"));
        } catch (AuthenticationException e) {
            return false;
        }
        return true;
    }
}
