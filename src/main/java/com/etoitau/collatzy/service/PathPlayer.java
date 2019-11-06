package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.NodeWithResult;

public interface PathPlayer {
    boolean hasNext();
    NodeWithResult next();
    void restart();
}
