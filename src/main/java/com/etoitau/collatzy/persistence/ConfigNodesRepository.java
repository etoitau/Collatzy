package com.etoitau.collatzy.persistence;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.NodeWithResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigNodesRepository extends JpaRepository<ConfigEntry, Long> {

}
