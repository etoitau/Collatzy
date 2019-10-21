package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.Objects;

public interface DeterminedPathNode {

    public BigInteger getValue();

    void setNext(DeterminedPathNode next);

    DeterminedPathNode getNext();

    void setResult(ResultState result);

    ResultState getResult();

    void setConfig(CollatzConfig config);

    CollatzConfig getConfig();

}
