package com.gsc.bm.server.repo.external;

import org.springframework.data.repository.CrudRepository;

public interface UserCredentialsRepository extends CrudRepository<UserCredentialsRecord, String> {
}
