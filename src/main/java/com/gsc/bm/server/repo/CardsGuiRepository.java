package com.gsc.bm.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsGuiRepository extends JpaRepository<CardsGuiRecord, String> {
}
