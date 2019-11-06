package com.etoitau.collatzy.domain;

import java.math.BigInteger;

/**
 * A node with a value, next link, and associated Collatz-like problem configuration
 */
public interface CollatzNode {
    BigInteger getValue();

    void setNext(NodeWithResult next);

    NodeWithResult getNext();

    void setConfig(CollatzConfig config);

    CollatzConfig getConfig();
}
