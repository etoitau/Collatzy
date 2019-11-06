package com.etoitau.collatzy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberMapTest {
    static CollatzConfig config;
    static NodeWithResult two;
    static NodeWithResult inMap;

    static ResultState result;
    static NodeWithResult update;
    static NumberMap map;

    @BeforeEach
    void clear() {
        config = new CollatzConfig();
        result = new ResultState(ResultState.Result.LOOP);
        map = new NumberMap(config);
        two = new PathNode(new BigInteger("2"));
        inMap = new PathNode(new BigInteger("1"), two, config,
                new ResultState(ResultState.Result.OPEN));
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
        NodeWithResult old = map.add(update);
        // old version should be the one we added before, not updated
        assertEquals(old.getResult().getResult(), ResultState.Result.OPEN);
        // one we added should be updated
        assertEquals(update.getNext(), two);
        // version in map now should have next and result loop
        NodeWithResult found = map.get(inMap.getValue());
        assertEquals(found.getResult().getResult(), ResultState.Result.LOOP);
        assertEquals(found.getNext(), two);
    }

    @Test
    void addAll() {
        // list dpn
        List<NodeWithResult> nodes = Arrays.asList(inMap, two);
        assertFalse(map.contains(inMap));
        map.addAll(nodes);
        assertTrue(map.contains(inMap));
        assertTrue(map.contains(two));
    }

    @Test
    void get() {
        map.add(two);
        NodeWithResult found = map.get(new BigInteger("2"));
        assertEquals(found, two);
    }

    @Test
    void serializationTest() {
        NodeWithResult eight, four, two, one;
        one = new PathNode(new BigInteger("1"));
        two = new PathNode(new BigInteger("2"));
        four = new PathNode(new BigInteger("4"));
        eight = new PathNode(new BigInteger("8"));
        one.setNext(four);
        two.setNext(one);
        four.setNext(two);
        eight.setNext(four);
        ResultState loop = new ResultState(ResultState.Result.LOOP);
        loop.addLoop(one);

        one.setResult(loop);
        two.setResult(loop);
        four.setResult(loop);

        map.add(one);
        map.add(two);
        map.add(four);
        map.add(eight);

        String serial = map.nodesToString();
        NumberMap de = NumberMap.mapFromNodeString(map.getConfig(), serial);

        // has one and four and four is next for one
        assertEquals(four.getValue(), de.get(one.getValue()).getNext().getValue());

        // eight has result open
        assertEquals(ResultState.Result.OPEN, de.get(eight.getValue()).getResult().getResult());

        // one has result loop and has loop of nodes
        assertEquals(ResultState.Result.LOOP, de.get(one.getValue()).getResult().getResult());
        assertEquals(3, de.get(one.getValue()).getResult().getLoopNodes().size());
    }
}