package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.CollatzConfig;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class CollatzCalculatorTest {

    @Test
    void next() {
        CollatzCalculator calc = new CollatzCalculator(new CollatzConfig());
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        BigInteger four = new BigInteger("4");

        assertEquals(one, calc.next(two));
        assertEquals(two, calc.next(four));
        assertEquals(four, calc.next(one));

        // test alt config
        calc = new CollatzCalculator(new CollatzConfig(2, 2, 1));
        BigInteger three = new BigInteger("3");
        BigInteger seven = new BigInteger("7");

        assertEquals(one, calc.next(two));
        assertEquals(three, calc.next(one));
        assertEquals(seven, calc.next(three));
    }
}