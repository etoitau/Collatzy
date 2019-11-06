package com.etoitau.collatzy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository object interface for configs table
 */
@Repository
public interface ConfigNodesRepository extends JpaRepository<ConfigEntry, Long> {
    List<ConfigEntry> findByDAndMAndP(int d, int m, int p);
}
