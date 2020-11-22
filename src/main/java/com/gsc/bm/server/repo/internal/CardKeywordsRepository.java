package com.gsc.bm.server.repo.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardKeywordsRepository extends JpaRepository<CardKeywordsRecord, String> {
}
