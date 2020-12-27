package com.gsc.bm.server.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthTokenOrFail(String username, String password) {
        //TODO do proper auth
        if (username.equals("gesucca") && passwordEncoder.matches(password, "$2a$10$xSRuxxJQ64ZFKJoz/vGmZe7lHaMpOaCftJjBBVC0pRE5y/iEQhMVS"))
            return new UsernamePasswordAuthenticationToken(username, null, Collections.singleton((GrantedAuthority) () -> "USER"));
        else throw new BadCredentialsException("Bad credentials for user " + username);

        /*
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }
         */
    }
}
