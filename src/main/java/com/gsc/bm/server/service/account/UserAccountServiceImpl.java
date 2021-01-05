package com.gsc.bm.server.service.account;

import com.gsc.bm.server.repo.external.UserCredentialsRecord;
import com.gsc.bm.server.repo.external.UserCredentialsRepository;
import com.gsc.bm.server.service.account.model.UserAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserCredentialsRepository userCredentialsRepo;

    @Autowired
    public UserAccountServiceImpl(UserCredentialsRepository userCredentialsRepo) {
        this.userCredentialsRepo = userCredentialsRepo;
    }

    @Override
    public UserAccountInfo loadUserAccountInfo(String username) {
        Optional<UserCredentialsRecord> rec = userCredentialsRepo.findById(username);
        if (rec.isPresent())
            return new UserAccountInfo(rec.get().getUsername(), rec.get().getEmail(), rec.get().getRole());
        return null;
    }
}
