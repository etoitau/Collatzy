package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Path implements NodePath {
    private NodeWithResult start;
    private List<NodeWithResult> nodeList;
    private List<BigInteger> valueList;

    public Path(NodeWithResult start) {
        this.start = start;
        nodeList = new ArrayList<>();
        valueList = new ArrayList<>();
    }

    private void popLists() {
        NodeWithResult cursor = start;
        List<NodeWithResult> loop = start.getResult().getLoopNodes();

        while (cursor != null && !loop.contains(cursor)) {
            nodeList.add(cursor);
            valueList.add(cursor.getValue());
            cursor = cursor.getNext();
        }
        if (cursor == null || loop.isEmpty()) { return; }
        NodeWithResult loopStart = cursor;

        while (!cursor.getNext().equals(loopStart)) {
            nodeList.add(cursor);
            valueList.add(cursor.getValue());
            cursor = cursor.getNext();
        }
        nodeList.add(cursor);
        valueList.add(cursor.getValue());
    }

    @Override
    public List<NodeWithResult> getPath() {
        if (nodeList.isEmpty()) popLists();
        return nodeList;
    }

    @Override
    public List<BigInteger> getValuePath() {
        if (valueList.isEmpty()) popLists();
        return valueList;
    }
}
