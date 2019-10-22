package com.etoitau.collatzy.domain;

import com.etoitau.collatzy.service.PathNodeBuilder;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberMap {
    private final CollatzConfig config;
    private Map<BigInteger, DeterminedPathNode> nodes;

    public NumberMap(CollatzConfig config) {
        this.config = config;
        nodes = new HashMap<>();
    }

    public boolean contains(DeterminedPathNode pn) {
        return nodes.containsValue(pn);
    }

    public boolean contains(BigInteger val) {
        return nodes.containsKey(val);
    }

    public DeterminedPathNode add(DeterminedPathNode toAdd) {
        if (contains(toAdd)) {
            // if already have entry, update toAdd to include all available info
            DeterminedPathNode prev = nodes.get(toAdd.getValue());
            PathNodeBuilder.combineDeterminedPathNodes(toAdd, prev);
        }
        return nodes.put(toAdd.getValue(), toAdd);
    }

    public void addAll(List<DeterminedPathNode> nodesToAdd) {
        for (DeterminedPathNode node: nodesToAdd) {
            add(node);
        }
    }

    public DeterminedPathNode get(BigInteger val) {
        return nodes.get(val);
    }
}
