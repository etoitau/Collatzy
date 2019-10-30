package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.List;

public interface NodePath {
    List<NodeWithResult> getPath();
    List<BigInteger> getValuePath();
}
