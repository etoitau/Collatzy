package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.DeterminedPathNode;
import com.etoitau.collatzy.domain.ResultState;
import sun.plugin.dom.exception.InvalidStateException;

import java.math.BigInteger;

public class DeterminedPathNodeBuilder {
    DeterminedPathNode node;

    public DeterminedPathNodeBuilder(BigInteger val) {
        node = new DeterminedPathNode(val);
    }

    public DeterminedPathNodeBuilder addNext(DeterminedPathNode n) {
        node.setNext(n);
        return this;
    }

    public DeterminedPathNodeBuilder addResult(ResultState rs) {
        node.setResult(rs);
        return this;
    }

    public DeterminedPathNodeBuilder addConfig(CollatzConfig cc) {
        node.setConfig(cc);
        return this;
    }

    public DeterminedPathNodeBuilder combineWith(DeterminedPathNode other) throws InvalidStateException {

        if (!combineDeterminedPathNodes(node, other)) {
            throw new InvalidStateException("Nodes are not equal");
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
