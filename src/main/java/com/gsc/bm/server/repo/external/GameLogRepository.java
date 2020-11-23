package com.gsc.bm.server.repo.external;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLogRepository extends CrudRepository<GameLogRecord, String> {

}
