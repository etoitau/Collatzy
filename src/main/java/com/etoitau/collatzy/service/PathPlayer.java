package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.DeterminedPathNode;

public interface PathPlayer {
    public boolean hasNext();
    public DeterminedPathNode next();
    public void restart();
}
