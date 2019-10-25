package com.etoitau.collatzy.domain;

public interface PathReport {
    String jsonReport();

    String htmlReport();

    boolean isUnknown();

    String lastNum();
}
