package com.gsc.bm.server.service.session;

import com.gsc.bm.server.repo.external.UserCredentialsRecord;
import com.gsc.bm.server.repo.external.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserCredentialsRepository userCredentialsRepo;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserCredentialsRepository userCredentialsRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userCredentialsRepo = userCredentialsRepo;
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthTokenOrFail(String username, String password) {
        if (username == null || username.trim().isEmpty())
            throw new AuthenticationCredentialsNotFoundException("USERNAME was null or empty");

        if (password == null || password.trim().isEmpty())
            throw new AuthenticationCredentialsNotFoundException("PASSWORD was null or empty");

        Optional<UserCredentialsRecord> rec = userCredentialsRepo.findById(username);
        if (rec.isEmpty())
            throw new BadCredentialsException("USERNAME " + username + " is not registered");
        else {
            if (!passwordEncoder.matches(password, rec.get().getSaltedHash()))
                throw new BadCredentialsException("Invalid PASSWORD for USER " + username);
            // if you are here, everything is fine
            return new UsernamePasswordAuthenticationToken(
                    username, null,
                    Collections.singleton((GrantedAuthority) () -> "USER"));
        }
    }
}
