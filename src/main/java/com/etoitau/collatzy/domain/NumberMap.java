package com.etoitau.collatzy.domain;

import com.etoitau.collatzy.service.PathNodeBuilder;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NumberMap {
    private final CollatzConfig config;
    private Map<BigInteger, NodeWithResult> nodes;

    public NumberMap(CollatzConfig config) {
        this.config = config;
        nodes = new HashMap<>();
    }

    public boolean contains(NodeWithResult pn) {
        return nodes.containsValue(pn);
    }

    public boolean contains(BigInteger val) {
        return nodes.containsKey(val);
    }

    public NodeWithResult add(NodeWithResult toAdd) {
        if (contains(toAdd)) {
            // if already have entry, update toAdd to include all available info
            NodeWithResult prev = nodes.get(toAdd.getValue());
            PathNodeBuilder.combineDeterminedPathNodes(toAdd, prev);
        }
        return nodes.put(toAdd.getValue(), toAdd);
    }

    public void addAll(Collection<NodeWithResult> nodesToAdd) {
        for (NodeWithResult node: nodesToAdd) {
            add(node);
        }
    }

    public NodeWithResult get(BigInteger val) {
        return nodes.get(val);
    }

    public CollatzConfig getConfig() { return config; }

    public Collection<NodeWithResult> getNodes() {
        return nodes.values();
    }
}
