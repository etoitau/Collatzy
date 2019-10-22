package com.etoitau.collatzy.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberMapTest {
    static CollatzConfig config;
    static DeterminedPathNode two;
    static DeterminedPathNode inMap;

    static ResultState result;
    static DeterminedPathNode update;
    static NumberMap map;

    @BeforeEach
    void clear() {
        config = new CollatzConfig();
        result = new ResultState(ResultState.Result.LOOP);
        map = new NumberMap(config);
        inMap = new PathNode(new BigInteger("1"), two, config,
                new ResultState(ResultState.Result.OPEN));
        two = new PathNode(new BigInteger("2"));
        update = new PathNode(new BigInteger("1"), null, null, result);
        map = new NumberMap(config);
    }

    @Test
    void contains() {
        // add by node
        // starts empty, shouldn't find node
        assertFalse(map.contains(inMap));
        map.add(inMap);
        // now should find the one we added but no other
        assertTrue(map.contains(inMap));
        assertFalse(map.contains(two));
    }

    @Test
    void contains1() {
        // by BigInteger
        BigInteger twoInt = new BigInteger("2");
        BigInteger oneInt = new BigInteger("1");
        // starts empty, shouldn't find by BI
        assertFalse(map.contains(oneInt));
        map.add(inMap);
        // now should find by the one we added but no other
        assertTrue(map.contains(oneInt));
        assertFalse(map.contains(twoInt));
    }

    @Test
    void add() {
        // testing that adding a node with same value as existing results in map containing updated node

        // add version with next and result open
        map.add(inMap);
        // add update with no next but result loop, and catch old version
        DeterminedPathNode old = map.add(update);
        // old version should be the one we added before, not updated
        assertEquals(old.getResult().getResult(), ResultState.Result.OPEN);
        // one we added should be updated
        assertEquals(update.getNext(), two);
        // version in map now should have next and result loop
        DeterminedPathNode found = map.get(inMap.getValue());
        assertEquals(found.getResult().getResult(), ResultState.Result.LOOP);
        assertEquals(found.getNext(), two);
    }

    @Test
    void addAll() {
        // list dpn
        List<DeterminedPathNode> nodes = Arrays.asList(inMap, two);
        assertFalse(map.contains(inMap));
        map.addAll(nodes);
        assertTrue(map.contains(inMap));
        assertTrue(map.contains(two));
    }

    @Test
    void get() {
        map.add(two);
        DeterminedPathNode found = map.get(new BigInteger("2"));
        assertEquals(found, two);
    }
}