package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.CollatzConfig;

import java.math.BigInteger;

/**
 * Object for determining next value in path if not yet known
 */
public class CollatzCalculator {
    private BigInteger d, m, p;

    public CollatzCalculator(CollatzConfig config) {
        this.d = BigInteger.valueOf(config.getD());
        this.m = BigInteger.valueOf(config.getM());
        this.p = BigInteger.valueOf(config.getP());
    }

    public BigInteger next(BigInteger prev) {
        if (prev.mod(d).equals(BigInteger.ZERO)) {
            return prev.divide(d);
        } else {
            return prev.multiply(m).add(p);
        }
    }
}
