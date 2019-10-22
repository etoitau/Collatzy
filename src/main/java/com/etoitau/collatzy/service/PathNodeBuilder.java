package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.DeterminedPathNode;
import com.etoitau.collatzy.domain.PathNode;
import com.etoitau.collatzy.domain.ResultState;

import java.math.BigInteger;

public class PathNodeBuilder {
    private DeterminedPathNode node;

    public PathNodeBuilder(BigInteger val) {
        node = new PathNode(val);
    }

    public PathNodeBuilder addNext(DeterminedPathNode n) {
        node.setNext(n);
        return this;
    }

    public PathNodeBuilder addResult(ResultState rs) {
        node.setResult(rs);
        return this;
    }

    public PathNodeBuilder addConfig(CollatzConfig cc) {
        node.setConfig(cc);
        return this;
    }

    public DeterminedPathNode getNode() {
        return node;
    }

    public PathNodeBuilder combineWith(DeterminedPathNode other) throws Exception {

        if (!combineDeterminedPathNodes(node, other)) {
            throw new Exception("Nodes are not equal");
        }
        return this;
    }

    public static boolean combineDeterminedPathNodes(DeterminedPathNode base, DeterminedPathNode addThis) {
        // value, config
        if (!base.equals(addThis)) {
            return false;
        }
        // next
        if (base.getNext() == null) {
            base.setNext(addThis.getNext());
        }
        // ResultState
        if (base.getResult() == null || base.getResult().getResult() == ResultState.Result.OPEN) {
            if (addThis.getResult() != null) {
                base.setResult(addThis.getResult());
            }
        }
        return true;
    }
}
