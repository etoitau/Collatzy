package com.etoitau.collatzy.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Object holds information about where a path will end up
 */
public class ResultState {
    public enum Result {
        OPEN,
        LOOP,
    }
    private final Result result;
    private List<NodeWithResult> loopNodes;

    public ResultState(Result result) {
        this.result = result;
        this.loopNodes = new ArrayList<>();
    }

    public void addLoop(NodeWithResult start) {
        loopNodes.add(start);
        NodeWithResult current = start;
        while(current.getNext() != start) {
            current = current.getNext();
            loopNodes.add(current);
        }
    }

    void addLoop(List<NodeWithResult> nodes) {
        loopNodes = nodes;
    }

    public Result getResult() {
        return result;
    }

    public List<NodeWithResult> getLoopNodes() {
        return loopNodes;
    }

    String toStringVerbose() {
        switch (result) {
            case OPEN:
                return "Unknown";
            case LOOP:
                StringBuilder sb = new StringBuilder();
                sb.append("Terminates in the loop: ");
                for (NodeWithResult pn: loopNodes) {
                    sb.append(pn.getValue()).append(", ");
                }
                sb.append(loopNodes.get(0).getValue()).append("...");
                return sb.toString();
            default:
                return "ERROR";
        }
    }

    @Override
    public String toString() {
        if (result != Result.LOOP) {
            return Result.OPEN.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(result);
        for (NodeWithResult node: loopNodes) {
            sb.append(" ").append(node.getValue().toString());
        }
        return sb.toString();
    }
}
