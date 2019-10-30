package com.etoitau.collatzy.domain;


public interface NodeWithResult extends CollatzNode{

    void setResult(ResultState result);

    ResultState getResult();

}
