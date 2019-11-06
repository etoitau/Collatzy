package com.etoitau.collatzy.domain;

/**
 * CollatzNode that carries information about where a path will end up from this point
 */
public interface NodeWithResult extends CollatzNode{

    void setResult(ResultState result);

    ResultState getResult();

}
