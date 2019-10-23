package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private static Path path;
    private static PathDriver pd;
    private static DeterminedPathNode eightNode, oneNode;
    private static Player p;

    @BeforeAll
    static void init() {
        BigInteger eightVal = new BigInteger("8");
        NumberMap map = new NumberMap(new CollatzConfig());
        pd = new PathDriver(new CollatzConfig(), map);
        eightNode = pd.startNewDrive(eightVal);
        pd.next(); // 4
        pd.next(); // 2
        oneNode = pd.next(); // 1
        path = new Path(eightNode);
        oneNode = new PathNode(new BigInteger("1"));
    }

    @BeforeEach
    void reset() {
        p = new Player(path);
    }

    @Test
    void hasNext() {
        assertTrue(p.hasNext());
        DeterminedPathNode cursor = null;
        while (p.hasNext()) {
            cursor = p.next();
        }
        assertEquals(oneNode, cursor);
        assertFalse(p.hasNext());
    }

    @Test
    void next() {
        // covered by hasNext
    }

    @Test
    void restart() {
        while (p.hasNext()) {
            p.next();
        }
        p.restart();
        assertEquals(eightNode, p.next());
    }
}