package com.gsc.bm.server.service.session;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

public interface AuthService {

    Optional<UsernamePasswordAuthenticationToken> getAuthToken(final String username, final String password);

}
