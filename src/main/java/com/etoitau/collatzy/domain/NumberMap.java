package com.etoitau.collatzy.domain;

import com.etoitau.collatzy.service.DeterminedPathNodeBuilder;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberMap {
    final CollatzConfig config;
    Map<BigInteger, DeterminedPathNode> nodes;

    public NumberMap(CollatzConfig config) {
        this.config = config;
        nodes = new HashMap<>();
    }

    public boolean contains(PathNode pn) {
        return nodes.containsValue(pn);
    }

    public boolean contains(BigInteger val) {
        return nodes.containsKey(val);
    }

    public DeterminedPathNode add(PathNode toAdd) {
        return nodes.put(toAdd.getVal(), new DeterminedPathNode(toAdd));
    }

    public DeterminedPathNode add(DeterminedPathNode toAdd) {
        if (contains(toAdd)) {
            DeterminedPathNode prev = nodes.get(toAdd.getVal());
            DeterminedPathNodeBuilder.combineDeterminedPathNodes(prev, toAdd);
            return prev;
        } else {
            return nodes.put(toAdd.getVal(), toAdd);
        }
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
