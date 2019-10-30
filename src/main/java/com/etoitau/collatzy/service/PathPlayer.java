package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.NodeWithResult;

public interface PathPlayer {
    public boolean hasNext();
    public NodeWithResult next();
    public void restart();
}
