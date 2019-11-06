package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.List;

/**
 * get a list of nodes or node values representing a path taken following next links
 */
public interface NodePath {
    List<NodeWithResult> getPath();
    List<BigInteger> getValuePath();
}
