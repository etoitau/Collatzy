package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Object for collecting and returning a node path
 */
public class Path implements NodePath {
    private NodeWithResult start;
    private List<NodeWithResult> nodeList;
    private List<BigInteger> valueList;

    public Path(NodeWithResult start) {
        this.start = start;
        nodeList = new ArrayList<>();
        valueList = new ArrayList<>();
    }

    /**
     * From the provided starting point, collect all nodes in the path from there
     * This is called in lazy way if user asks for list of nodes or values
     */
    private void popLists() {
        NodeWithResult cursor = start;
        List<NodeWithResult> loop = start.getResult().getLoopNodes();

        // first get nodes from start until either run out of nodes, or hit loop
        while (cursor != null && !loop.contains(cursor)) {
            nodeList.add(cursor);
            valueList.add(cursor.getValue());
            cursor = cursor.getNext();
        }

        // if ran out of nodes, done, return
        if (cursor == null) { return; }

        // otherwise must have hit loop. Collect nodes in loop
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
