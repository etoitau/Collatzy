package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Path implements NodePath {
    private DeterminedPathNode start;
    private List<DeterminedPathNode> nodeList;
    private List<BigInteger> valueList;

    public Path(DeterminedPathNode start) {
        this.start = start;
        nodeList = new ArrayList<>();
        valueList = new ArrayList<>();
    }

    private void popLists() {
        DeterminedPathNode cursor = start;
        List<DeterminedPathNode> loop = start.getResult().getLoopNodes();

        while (cursor != null && !loop.contains(cursor)) {
            nodeList.add(cursor);
            valueList.add(cursor.getValue());
            cursor = cursor.getNext();
        }
        if (cursor == null || loop.isEmpty()) { return; }
        DeterminedPathNode loopStart = cursor;
        while (cursor.getNext() != loopStart) {
            nodeList.add(cursor);
            valueList.add(cursor.getValue());
            cursor = cursor.getNext();
        }
    }

    @Override
    public List<DeterminedPathNode> getPath() {
        if (nodeList.isEmpty()) popLists();
        return nodeList;
    }

    @Override
    public List<BigInteger> getValuePath() {
        if (valueList.isEmpty()) popLists();
        return valueList;
    }
}
