package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ResultState {
    public enum Result {
        OPEN,
        LOOP,
    }
    private final Result result;
    private List<DeterminedPathNode> loopNodes;

    public ResultState(Result result) {
        this.result = result;
        this.loopNodes = new ArrayList<>();
    }

    public void addLoop(DeterminedPathNode start) {
        loopNodes.add(start);
        DeterminedPathNode current = start;
        while(current.getNext() != start) {
            current = current.getNext();
            loopNodes.add(current);
        }
    }

    public void addLoop(List<DeterminedPathNode> nodes) {
        loopNodes = nodes;
    }

    public Result getResult() {
        return result;
    }

    public List<DeterminedPathNode> getLoopNodes() {
        return loopNodes;
    }

    public String toStringVerbose() {
        switch (result) {
            case OPEN:
                return "unknown";
            case LOOP:
                StringBuilder sb = new StringBuilder();
                sb.append("terminates in the loop: ");
                for (DeterminedPathNode pn: loopNodes) {
                    sb.append(pn.getValue()).append(", ");
                }
                sb.append(loopNodes.get(0).getValue()).append("...");
                return sb.toString();
            default:
                return "ERROR";
        }
    }
}
