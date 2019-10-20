package com.etoitau.collatzy.domain;

import java.util.Objects;

public class CollatzConfig {
    private final int d, m, p;

    public CollatzConfig(int d, int m, int p) {
        this.d = d;
        this.m = m;
        this.p = p;
    }

    public int getD() {
        return d;
    }

    public int getM() {
        return m;
    }

    public int getP() {
        return p;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        CollatzConfig otherC;
        if (other instanceof CollatzConfig) {
            otherC = (CollatzConfig) other;
            return (this.d == otherC.getD() && this.m == otherC.getM() && this.p == otherC.getP());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(d, m, p);
    }
}
