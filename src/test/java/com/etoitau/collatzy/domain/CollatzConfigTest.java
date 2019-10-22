package com.etoitau.collatzy.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollatzConfigTest {
    static int d, m, p;
    static CollatzConfig config1, config2, config3, config4;

    @BeforeAll
    static void setup() {
        d = 2;
        m = 3;
        p = 1;
        config1 = new CollatzConfig(d, m, p);
        config2 = new CollatzConfig(d, m, p);
        config3 = new CollatzConfig(3, m, p);
        config4 = new CollatzConfig();
    }

    @Test
    void getD() {
        assertEquals(d, config1.getD());
    }

    @Test
    void getM() {
        assertEquals(m, config1.getM());
    }

    @Test
    void getP() {
        assertEquals(p, config1.getP());
    }

    @Test
    void equals1() {
        assertTrue(config1.equals(config2));
        assertFalse(config1.equals(config3));
        assertTrue(config4.equals(config1));
    }

    @Test
    void hashCode1() {
        assertEquals(config1.hashCode(), config2.hashCode());
    }
}