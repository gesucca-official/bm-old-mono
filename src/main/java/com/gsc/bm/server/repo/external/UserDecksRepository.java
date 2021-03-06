package com.gsc.bm.server.repo.external;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDecksRepository extends CrudRepository<UserDecksRecord, UserDeckRecordKey> {

    List<UserDecksRecord> findAllByUsername(String username);
}
