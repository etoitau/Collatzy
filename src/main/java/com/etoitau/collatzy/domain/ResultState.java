package com.etoitau.collatzy.domain;

import java.util.ArrayList;
import java.util.List;

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

    public void addLoop(List<NodeWithResult> nodes) {
        loopNodes = nodes;
    }

    public Result getResult() {
        return result;
    }

    public List<NodeWithResult> getLoopNodes() {
        return loopNodes;
    }

    public String toStringVerbose() {
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
}
