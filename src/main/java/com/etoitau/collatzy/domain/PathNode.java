package com.etoitau.collatzy.domain;

import java.math.BigInteger;

/**
 * The basic unit of information when exploring a Collatz configuration
 * A node has a number value, the node that follows from it, it's associated configuration, and information about where
 * a path from it will end up
 */
public class PathNode implements NodeWithResult {
    private BigInteger val;
    private NodeWithResult next;
    private CollatzConfig config;
    private ResultState result;

    public PathNode(BigInteger val, NodeWithResult next, CollatzConfig config, ResultState result) {
        this.val = val;
        this.next = next;
        this.config = config;
        this.result = result;
    }

    public PathNode(BigInteger val) {
        this(val, null, new CollatzConfig(), new ResultState(ResultState.Result.OPEN));
    }

    public PathNode(BigInteger val, CollatzConfig config) {
        this(val, null, config, new ResultState(ResultState.Result.OPEN));
    }

    @Override
    public BigInteger getValue() {
        return val;
    }

    @Override
    public void setNext(NodeWithResult next) {
        this.next = next;
    }

    @Override
    public NodeWithResult getNext() {
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
