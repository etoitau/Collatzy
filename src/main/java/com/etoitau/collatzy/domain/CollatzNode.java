package com.etoitau.collatzy.domain;

import java.math.BigInteger;

public interface CollatzNode {
    BigInteger getValue();

    void setNext(NodeWithResult next);

    NodeWithResult getNext();

    void setConfig(CollatzConfig config);

    CollatzConfig getConfig();
}
