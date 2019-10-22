package com.etoitau.collatzy.domain;


import java.math.BigInteger;
import java.util.Objects;

public class PathNode implements DeterminedPathNode {
    private BigInteger val;
    private DeterminedPathNode next;
    private CollatzConfig config;
    private ResultState result;

    public PathNode(BigInteger val, DeterminedPathNode next, CollatzConfig config, ResultState result) {
        this.val = val;
        this.next = next;
        this.config = config;
        this.result = result;
    }

    public PathNode(BigInteger val) {
        this(val, null, new CollatzConfig(), new ResultState(ResultState.Result.OPEN));
    }


    @Override
    public BigInteger getValue() {
        return val;
    }

    @Override
    public void setNext(DeterminedPathNode next) {
        this.next = next;
    }

    @Override
    public DeterminedPathNode getNext() {
        return next;
    }

    @Override
    public void setResult(ResultState result) {
        this.result = result;
    }

    @Override
    public ResultState getResult() {
        return result;
    }

    @Override
    public void setConfig(CollatzConfig config) {
        this.config = config;
    }

    @Override
    public CollatzConfig getConfig() {
        return config;
    }

    /**
     * Want equals to have any PathNodes match if value is the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass().isAssignableFrom(this.getClass())) {
            PathNode pathNode = (PathNode) o;
            return val.equals(pathNode.getValue());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return val.hashCode() + config.hashCode();
    }
}
