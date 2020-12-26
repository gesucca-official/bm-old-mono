package com.gsc.bm.server.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UsernamePasswordAuthenticationToken> getAuthToken(String username, String password) {
        //TODO do proper auth
        if (!username.equals("gesucca")
                // lamiapassword
                || !passwordEncoder.matches(password, "$2a$10$xSRuxxJQ64ZFKJoz/vGmZe7lHaMpOaCftJjBBVC0pRE5y/iEQhMVS"))
            return Optional.empty();
        else
            return Optional.of(new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singleton((GrantedAuthority) () -> "USER") // MUST provide at least one role
            ));
    }
}
