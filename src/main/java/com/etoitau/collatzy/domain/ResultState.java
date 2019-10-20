package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ResultState {
    public enum Result {
        OPEN,
        VALUE,
        LOOP,
        INF
    }
    private final Result result;
    private BigInteger val;
    private List<PathNode> loopNodes;

    public ResultState(Result result) {
        this.result = result;
    }

    public void addLoop(PathNode start) {
        loopNodes = new ArrayList<>();
        loopNodes.add(start);
        PathNode current = start;
        while(current.getNext() != start) {
            current = current.getNext();
            loopNodes.add(current);
        }
    }

    public void addLoop(List<PathNode> nodes) {
        loopNodes = nodes;
    }

    public void addVal(BigInteger val) {
        this.val = val;
    }

    public Result getResult() {
        return result;
    }

    public BigInteger getVal() {
        return val;
    }

    public List<PathNode> getLoopNodes() {
        return loopNodes;
    }

    public String toStringVerbose() {
        switch (result) {
            case OPEN:
                return "unknown";
            case INF:
                return "appears to blow up";
            case LOOP:
                StringBuilder sb = new StringBuilder();
                sb.append("stuck in the loop: ");
                for (PathNode pn: loopNodes) {
                    sb.append(pn.getVal()).append(", ");
                }
                sb.append(loopNodes.get(0).getVal()).append("...");
                return sb.toString();
            case VALUE:
                return "ends up at " + val;
            default:
                return "ERROR";
        }
    }
}
