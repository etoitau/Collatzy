package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.Objects;

public class DeterminedPathNode extends PathNode {
    private CollatzConfig config;
    private ResultState result;

    public DeterminedPathNode(BigInteger val, PathNode next) {
        super(val, next);
    }

    public DeterminedPathNode(BigInteger val) {
        super(val, null);
    }

    public DeterminedPathNode(PathNode pn) {
        super(pn.getVal(), pn.getNext());
    }

    public DeterminedPathNode(PathNode pn, CollatzConfig config, ResultState result) {
        super(pn.getVal(), pn.getNext());
        setConfig(config);
        setResult(result);
    }

    public DeterminedPathNode setResult(ResultState result) {
        this.result = result;
        return this;
    }

    public ResultState getResult() {
        return result;
    }

    public DeterminedPathNode setConfig(CollatzConfig config) {
        this.config = config;
        return this;
    }

    public CollatzConfig getConfig() {
        return config;
    }

//    /**
//     * Want equals to have any PathNodes match if value is the same and same CollatzConfig
//     */
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null) return false;
//        if (o.getClass().isAssignableFrom(this.getClass())) {
//            DeterminedPathNode pathNode = (DeterminedPathNode) o;
//            return val.equals(pathNode.val) && config.equals(pathNode.getConfig());
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(val) + Objects.hash(config);
//    }

}
