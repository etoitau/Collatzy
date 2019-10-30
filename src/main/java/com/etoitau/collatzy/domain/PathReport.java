package com.etoitau.collatzy.domain;

import java.util.List;

public interface PathReport {

    boolean isUnknown();

    String lastNum();

    String getStartingPoint();

    String getResult();

    Integer getLength();

    List<String> getStringValueList();
}
