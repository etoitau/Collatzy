package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.DeterminedPathNode;
import com.etoitau.collatzy.domain.Path;

import java.util.List;

public class Player implements PathPlayer {
    private List<DeterminedPathNode> listNodes;
    private int current = -1, next = -1;

    public Player(Path path) {
        listNodes = path.getPath();
        if (listNodes.isEmpty()) return;
        current = 0;
        next = 0;
    }

    @Override
    public boolean hasNext() {
        return next < listNodes.size();
    }

    @Override
    public DeterminedPathNode next() {
        current = next;
        next++;
        return listNodes.get(current);
    }

    @Override
    public void restart() {
        current = 0;
        next = 0;
    }
}
