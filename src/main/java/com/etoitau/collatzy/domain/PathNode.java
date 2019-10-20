package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.Objects;

public class PathNode {
    private final BigInteger val;
    private PathNode next;

    public PathNode(BigInteger val, PathNode next) {
        this.val = val;
        this.next = next;
    }

    public PathNode(BigInteger val) {
        this(val, null);
    }

    public BigInteger getVal() {
        return val;
    }

    public PathNode getNext() {
        return next;
    }

    public void setNext(PathNode next) {
        this.next = next;
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
            return val.equals(pathNode.val);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }
}
