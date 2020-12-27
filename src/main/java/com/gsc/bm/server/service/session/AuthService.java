package com.gsc.bm.server.service.session;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthService {

    UsernamePasswordAuthenticationToken getAuthTokenOrFail(final String username, final String password);

}
